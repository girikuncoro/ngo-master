package query;

import java.util.Random;
/**
 * Created by nhosgur on 1/6/15.
 */
public class RandomGenCodeGenerator {
    private static final String ALL_AVAILABLES="abcdefhjklmnprstuvwyzABCDEFHJKMNPQRSTUVWYZ2345678";
	private static volatile int NUMBER_OF_CHAR = 2;
	private static final int POOL_SIZE=ALL_AVAILABLES.length();
	private static Random random = new Random();
	static{
		random.setSeed(System.currentTimeMillis());
	}
	public static String generateItemCode(int numberOfEl){
		if(numberOfEl<=0){
			throw new IllegalArgumentException("Number of elements must be bigger than zero");
		}
		
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<numberOfEl;i++){
			int rn= Math.abs(random.nextInt() % POOL_SIZE);
			sb.append(ALL_AVAILABLES.charAt(rn));
		}
		
		return sb.toString();
	}
	
	public static String generateItemCode(){
        return generateItemCode(1);
	}
}
