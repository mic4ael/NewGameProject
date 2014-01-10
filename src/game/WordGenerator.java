package game;

public class WordGenerator {
	private static String []words;
	
	static {
		words = new String[] {
			"lampa", "piłka", "księżyc",
			"stół", "długopis", "zeszyt",
			"podróż", "lodówka", "komputer",
			"słoik", "kieliszek", "monitor",
			"samochód", "rower", "poduszka"
		};
	}
	
	public static String nextWord() {
		return words[(int)Math.round(Math.random() * (words.length - 1))];
	}
}
