package src.model;

public class AssignmentStatus {
	private int id;
	private String status;
	private String icon = null;
	private String color;
	private String createdAt = null;
	private String updatedAt = null;

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public String getIcon() {
		return icon;
	}

	public String getColor() {
		return color;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}