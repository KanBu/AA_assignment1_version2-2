
public class NKListTest {
	

	
	public static void main(String Args[]){
		NKList test1 = new NKList();	
		String s1 = new String("A1");
		String s2 = new String("A2");
		String s3 = new String("A3");
		String s4 = new String("A4");
		
		test1.addVertice(s1);
		test1.addVertice(s2);
		
		test1.addVertice(s3);
		
		test1.addVertice(s4, 3);
		
		test1.deleteVertice(s1);
		
		
		for(int i = 1; i <= test1.getLength(); i++){
			System.out.println("the " + i + "th: " + test1.getVertice(i-1));
		}
		System.out.println("length is: " + test1.getLength());
		
		
			
		
	}
	
	public boolean testBreakInForLoop(String str){
		for(int i = 1; i < 2; i++){
			if(str.equals("break"))
				return true;
		}
		return false;
	}
}
