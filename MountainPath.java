

/**
 * This class contains the logic for moving a mountaineer through a
 * mountainous region. Essentially, the method startHike() will
 * simulate the mountaineer moving through the region, by always
 * having the mountaineer choose a path forward, such that the
 * absolute change in elevation is the smallest possible, given the
 * different alternatives
 * 
 * @author Kristian Nybom
 *
 */
public class MountainPath {

	private Topography topo;     // The topography of the mountainous region 
	private Mountaineer mount;   // The mountaineer moving through the mountainous region
	
	public MountainPath(String filename) {
		
		/*
		 * DO NOT MODIFY THE CODE IN THIS CONSTRUCTOR
		 */
		
		topo = new Topography(filename);  // Create Topography object
		mount = new Mountaineer();        // Create the mountaineer
		
		// Set the starting location of the mountaineer
		mount.setStartingPoisition(topo.findLowestStartingPoint());
		
		// Display the current location of the mountaineer.
		System.out.println(mount);
	}

	/**
	 * TODO: Implement this method
	 * 
	 * This method should control the movement of the mountaineer
	 * through the mountainous region. When the mountaineer
	 * reaches the east end of the map, the message already
	 * implemented in this method should be displayed.
	 */
	public void startHike() {
		/*
		 * Insert your code here.
		 */
		while(move(scanNeighbours())){
			move(scanNeighbours());
		}
		/*
		 * DO NOT MODIFY THE System.out.print BELOW!
		 * DO NOT ADD ANY CODE AFTER THE System.out.print BELOW!
		 */
		System.out.print("Mountaineer reached the end of the map. Total elevation change: " + mount.getElevationChange());
	}

	/**
	 * TODO: Implement this method
	 * 
	 * Scans neighbouring cells and returns an integer, representing the direction to move in.
	 * The direction is chosen as the smallest absolute change in elevation when moving in that
	 * direction, based on the mountaineers current location. Note: this method only determines
	 * the direction to move in, but it should not move the mountaineer anywhere.
	 * 
	 * @return An integer representing the direction to move in. Value 0 = NE, 1 = E, and 2 = SE.
	 * Should the return value be anything else, it means that the mountaineer has reached the
	 * border of the map
	 */
	public int scanNeighbours() {
		/*
		skapar variabler som används i logiken
		 */
		Cell location = mount.getLocation();
		int row = location.getRow();
		int col = location.getCol();
		int[] elevs = topo.getNeighbouringElevations(row, col);
		int curElevation = location.getElevation();
		int delta0 = Math.abs(curElevation - elevs[0]);
		int delta1 = Math.abs(curElevation - elevs[1]);
		int delta2 = Math.abs(curElevation - elevs[2]);
		/*
		logiken som avgör vilken cell mountaineer skall gå till
	 */
		if(elevs[1] == elevs[2] && elevs[2] == Topography.OUTSIDE_BORDERS){
			return -1;
		}else if(delta0 < delta1 && delta0 < delta2){
			return 0;
		}else if(delta1 < delta0 && delta1 < delta2){
			return 1;
		}else if(delta2 < delta1 && delta2 < delta0){
			return 2;
		}else if(delta0 == delta1 && delta2 > delta0){
			return 1;
		}else if(delta1 == delta2 && delta0 > delta1){
			return 2;
		}else if(delta0 == delta2 && delta1 > delta0){
			return 2;
		}else {
			return 2;
		}
	}

	/**
	 * TODO: Implement this method
	 * 
	 * Moves the mountaineer in the specified direction
	 * @param direction The value of the parameter corresponds to the return value of the method 
	 * scanNeighbours, i.e. value 0 = NE, 1 = E, and 2 = SE.
	 * @return true on successful movement, false at the end of map
	 */
	public boolean move(int direction) {
		Cell[] locations = new Cell[3];
		Cell location = mount.getLocation();

		int row = location.getRow();
		int col = location.getCol();

		locations[0] = topo.getCell(row - 1, col + 1);
		locations[1] = topo.getCell(row, col + 1);
		locations[2] = topo.getCell(row + 1, col +1);

		if(direction == -1) {
			return false;
		}else if(direction == 2){
			mount.moveToCell(locations[2]);
			return true;
		}else if(direction == 1){
			mount.moveToCell(locations[1]);
			return true;
		}else {
			mount.moveToCell(locations[0]);
			return true;
		}
	}
}
