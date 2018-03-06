package squadShooter;

public class Camera {
	
	public float offsetX, offsetY;
	
	public Camera(float offsetX, float offsetY) {
		this.setOffsetX(offsetX);
		this.setOffsetY(offsetY);
	}

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float value) {
		offsetX = offsetX + value;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float value) {
		offsetY = offsetX + value;
	}
	

}
