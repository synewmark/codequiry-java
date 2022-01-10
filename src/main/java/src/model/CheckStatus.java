package src.model;

public class CheckStatus {
	private Check check;
	private String status;
	private boolean dbcheck;
	private boolean webcheck;
	private int submissionCount;
	private String checkURL;

	public Check getCheck() {
		return check;
	}

	public String getStatus() {
		return status;
	}

	public boolean getDbcheck() {
		return dbcheck;
	}

	public boolean getWebcheck() {
		return webcheck;
	}

	public int getSubmissionCount() {
		return submissionCount;
	}

	public String getCheckURL() {
		return checkURL;
	}

	public void setCheck(Check check) {
		this.check = check;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDbcheck(boolean dbcheck) {
		this.dbcheck = dbcheck;
	}

	public void setWebcheck(boolean webcheck) {
		this.webcheck = webcheck;
	}

	public void setSubmissionCount(int submissionCount) {
		this.submissionCount = submissionCount;
	}

	public void setCheckUrl(String checkUrl) {
		this.checkURL = checkUrl;
	}
}