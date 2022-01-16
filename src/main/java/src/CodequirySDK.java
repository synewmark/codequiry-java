package src;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.socket.client.IO;
import io.socket.client.Socket;
import src.exceptions.CodequiryApiException;
import src.model.Account;
import src.model.Check;
import src.model.CheckStatus;
import src.model.Overview;
import src.model.SubmissionResults;
import src.model.UploadResult;

public class CodequirySDK {

	private static final String ENCODING = "UTF8";

	private static final String API_BASE_URL = "https://codequiry.com/api/v1/";
	private static final String API_UPLOAD_URL = "https://codequiry.com/api/v1/check/upload";
	private static final String SOCKETS_BASE_URL = "https://api.codequiry.com/";

	private static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

			// naming convention is inconsistent
			// a robust solution would require many annotations or a custom deserializer
//			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

	private final Map<String, String> baseHeaders = new HashMap<String, String>();

	public CodequirySDK(String apiKey) {
		baseHeaders.put("Content-Type", "application/json");
		baseHeaders.put("apikey", apiKey);
	}

	public Account getAccount() {
		String json;
		try {
			json = new MultipartUtility(API_BASE_URL + "account", ENCODING, baseHeaders).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, Account.class);
	}

	public List<Check> getChecks() {
		try {
			String json = new MultipartUtility(API_BASE_URL + "checks", ENCODING, baseHeaders).finish();
			return objectMapper.readValue(json, new TypeReference<List<Check>>() {
			});
		} catch (IOException e) {
			throw new CodequiryApiException("Error parsing json response", e);
		}
	}

	public Check createCheck(String checkName, String lang) {
		Map<String, String> headers = new HashMap<>(baseHeaders);
		headers.put("Accept", "*/*");
		headers.put("name", checkName);
		headers.put("language", lang);
		String json;
		try {
			json = new MultipartUtility(
					String.format("%scheck/create?name=%s&language=%s", API_BASE_URL, checkName, lang), ENCODING,
					headers).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, Check.class);
	}

	public CheckStatus startCheck(Integer checkId) {
		Map<String, String> params = new HashMap<>(baseHeaders);
		params.put("check_id", checkId.toString());
		String json;
		try {
			json = new MultipartUtility(
					String.format("%scheck/start?check_id=%d?estimate_cost=1", API_BASE_URL, checkId), ENCODING,
					baseHeaders).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, CheckStatus.class);
	}

	public Check getCheck(Integer checkId) {
		Map<String, String> params = new HashMap<>(baseHeaders);
		params.put("check_id", checkId.toString());
		String json;
		try {
			json = new MultipartUtility(API_BASE_URL + "check/get", ENCODING, baseHeaders).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, Check.class);
	}

	public Overview getOverview(Integer checkId) {
		Map<String, String> params = new HashMap<>(baseHeaders);
		params.put("check_id", checkId.toString());
		String json;
		try {
			json = new MultipartUtility(String.format("%scheck/overview?check_id=%d", API_BASE_URL, checkId), ENCODING,
					baseHeaders).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, Overview.class);
	}

	public SubmissionResults getResults(Integer checkId, String submissionId) {
		Map<String, String> params = new HashMap<>(baseHeaders);
		params.put("check_id", checkId.toString());
		params.put("submission_id", submissionId);
		String json;
		try {
			json = new MultipartUtility(API_BASE_URL + "check/results", ENCODING, baseHeaders).finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, SubmissionResults.class);
	}

	// untested!!
	public void checkListen(String jobId, MessageHandler handler) {
		try {
			Socket socket = IO.socket(SOCKETS_BASE_URL);
			socket.on(Socket.EVENT_CONNECT, args -> {
				socket.emit("job-check", " {'jobid': " + jobId + "}");
				socket.disconnect();
			}).on("job-status", handler::handleMessage).on(Socket.EVENT_DISCONNECT, args -> {
			});

			socket.connect();
		} catch (URISyntaxException e) {
			throw new CodequiryApiException("Error connecting to socket.", e);
		}
	}

	public UploadResult upload(Integer checkId, String filePath) {
		File file = new File(filePath);
		Map<String, String> headers = new HashMap<>(baseHeaders);
		String json;
		try {
			MultipartUtility httpPost = new MultipartUtility(API_UPLOAD_URL, ENCODING, headers);
			httpPost.addFormField("check_id", checkId.toString());
			httpPost.addFilePart("file", file);
			json = httpPost.finish();
		} catch (IOException e) {
			throw new CodequiryApiException(e);
		}
		return mapJsonToObject(json, UploadResult.class);

	}

	private <T> T mapJsonToObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new CodequiryApiException("Error parsing json response", e);
		}
	}

	public interface MessageHandler {
		void handleMessage(Object[] data);
	}
}