public class RestrictedSpots {
	private int spotID;
	private String spotName;
	private int spotArea; //Area in m^2
	private int spotPermittedTime; //Time in seconds
	
	final int socialDistance = 1; //Social distancing distance in metres
	
	public RestrictedSpots(int id, String name, int area, int time){
		this.spotID = id;
		this.spotName = name;
		this.spotArea = area; 
		this.spotPermittedTime = time;
	}

	public int getArea(){
		return this.spotArea;
	}

	public int getID(){
		return this.spotID;
	}

	public String getString(){
		return this.spotName;
	}

	public int getTime(){
		return this.spotPermittedTime;
	}
}
