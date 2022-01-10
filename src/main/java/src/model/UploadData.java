package src.model;

import java.util.List;

public class UploadData {
	private float id;
	private String filename;
	private float statusId;
	private String createdAt;
	private String updatedAt;
	private String result1;
	private String result2;
	private String result3;
	private String totalResult;
	private String modifyUpdatedAt;
	private List<AssignmentStatus> assignmentStatuses;

	public float getId() {
		return id;
	}

	public void setId(float id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public float getStatusId() {
		return statusId;
	}

	public void setStatusId(float statusId) {
		this.statusId = statusId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getResult1() {
		return result1;
	}

	public void setResult1(String result1) {
		this.result1 = result1;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}

	public String getResult3() {
		return result3;
	}

	public void setResult3(String result3) {
		this.result3 = result3;
	}

	public String getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(String totalResult) {
		this.totalResult = totalResult;
	}

	public String getModifyUpdatedAt() {
		return modifyUpdatedAt;
	}

	public void setModifyUpdatedAt(String modifyUpdatedAt) {
		this.modifyUpdatedAt = modifyUpdatedAt;
	}

	public List<AssignmentStatus> getAssignmentStatuses() {
		return assignmentStatuses;
	}

	public void setAssignmentStatuses(List<AssignmentStatus> assignmentStatuses) {
		this.assignmentStatuses = assignmentStatuses;
	}
}