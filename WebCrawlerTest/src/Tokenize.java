import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Tokenize {
	
	
	public String textFile;
	
	
	
	@SuppressWarnings("null")
	public Tokenize(String textFile) throws FileNotFoundException, IOException {
		super();
		this.textFile = textFile;
		ArrayList<String> resultListSWR=tokenizeFileSWR(textFile);
		ArrayList<String> resultList=tokenizeFile(textFile);
		HashMap <String,Integer> frequencyMap=computeWordFrequencies(resultListSWR);
		TreeMap <String,Integer> sortedMap=print(frequencyMap);
		
		ArrayList<String> threeGrams=threeGramBuilder(resultList);
		ArrayList<String> threeGramsSWR=threeGramBuilder(resultListSWR);

		HashMap <String,Integer> frequencyMapthreeGrams=computeWordFrequencies(threeGrams);
		HashMap <String,Integer> frequencyMapthreeGramsSWR=computeWordFrequencies(threeGramsSWR);

		
		TreeMap <String,Integer> sortedMapthreeGrams=print(frequencyMapthreeGrams);
		TreeMap <String,Integer> sortedMapthreeGramsSWR=print(frequencyMapthreeGramsSWR);

		ArrayList<String> newThreeGrams=new ArrayList<>();
		
		
		for(String str: threeGramsSWR){
			if(threeGrams.contains(str)){
				newThreeGrams.add(str);
			}
		}
		
		
			
		
		
		PrintWriter writer = null;
		PrintWriter writerfreq = null;
		PrintWriter writerfreqthreegrams = null;
		PrintWriter threegrams = null;
		PrintWriter writerfreqthreegramsSWR=null;
		try {
			writer = new PrintWriter("D:\\crawlerDataTokenized\\tokens.txt", "UTF-8");
			writerfreq = new PrintWriter("D:\\crawlerDataTokenized\\tokenFrequency.txt", "UTF-8");
			writerfreqthreegrams = new PrintWriter("D:\\crawlerDataTokenized\\threegramFrequency.txt", "UTF-8");
			threegrams = new PrintWriter("D:\\crawlerDataTokenized\\threegrams.txt", "UTF-8");
			writerfreqthreegramsSWR= new PrintWriter("D:\\crawlerDataTokenized\\threegramsSWRfreq.txt", "UTF-8");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String s : resultList)
		{
			writer.println(s);
			
		}
		writer.close();
		
		for(String s : newThreeGrams)
		{
			threegrams.println(s);
			
		}
		threegrams.close();
		
		for (HashMap.Entry<String, Integer> entry : sortedMap.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            writerfreq.println("(Token, Frequency)::\t(" + key + ", " + value + ")");
        }
		writerfreq.close();
		
		
		
		for (HashMap.Entry<String, Integer> entry : sortedMapthreeGrams.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            writerfreqthreegrams.println("(Token, Frequency)::\t(" + key + ", " + value + ")");
        }
		writerfreqthreegrams.close();
		
		for (HashMap.Entry<String, Integer> entry : sortedMapthreeGramsSWR.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            writerfreqthreegramsSWR.println("(Token, Frequency)::\t(" + key + ", " + value + ")");
        }
		writerfreqthreegramsSWR.close();
		
		
		
		
		
		
		
		
	}
	
	

	public static ArrayList<String> tokenizeFileSWR (String textFile) throws FileNotFoundException, IOException {
        ArrayList<String> stopwords = new ArrayList<>();
        stopwords = stopwordsList();
		ArrayList<String> tokenList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(textFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = "";

        try {
            while ((s = br.readLine()) != null) {

                Pattern checkRegex = Pattern.compile("[A-Za-z0-9']{1,100}");
                Matcher regexMatcher = checkRegex.matcher(s);

                while (regexMatcher.find()) {
                    if (regexMatcher.group().length() != 0) {


                        tokenList.add((regexMatcher.group().trim().toLowerCase()));

                    }
                    tokenList.removeAll(stopwords);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return tokenList;

    }
	
	public static ArrayList<String> tokenizeFile (String textFile) throws FileNotFoundException, IOException {
        ArrayList<String> stopwords = new ArrayList<>();
        //stopwords = stopwordsList();
		ArrayList<String> tokenList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(textFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s = "";

        try {
            while ((s = br.readLine()) != null) {

                Pattern checkRegex = Pattern.compile("[A-Za-z0-9']{1,100}");
                Matcher regexMatcher = checkRegex.matcher(s);

                while (regexMatcher.find()) {
                    if (regexMatcher.group().length() != 0) {


                        tokenList.add((regexMatcher.group().trim().toLowerCase()));

                    }
                    //tokenList.removeAll(stopwords);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return tokenList;

    }
	
	public static HashMap<String, Integer> computeWordFrequencies(ArrayList<String> resultList) {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        for (String a : resultList) {
            Integer freq = m.get(a);
            m.put(a, (freq == null) ? 1 : freq + 1);
        }
        return m;

    }
	
	
	static TreeMap<String,Integer> print(HashMap<String, Integer> frequencyMap) {

        //System.out.println("======================================================================");
        //System.out.println("***************************FREQUENCY LIST*****************************");
        //System.out.println("======================================================================");

        ValueComparator vc =  new ValueComparator(frequencyMap);
        TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
        sortedMap.putAll(frequencyMap);
        //System.out.println(sortedMap);

        /*for (HashMap.Entry<String, Integer> entry : sortedMap.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            System.out.println("(Token, Frequency)::\t(" + key + ", " + value + ")");
        }*/
        //System.out.println("Total number of elements in the list from text file= "+sortedMap.size());
        //System.out.println("\n\n");
        return sortedMap;


    }
	
	public static ArrayList<String> stopwordsList() throws FileNotFoundException, IOException{
		
    	FileInputStream list = new FileInputStream("D:\\stopwords.txt");
        
		BufferedReader br1 = new BufferedReader(new InputStreamReader (list));
        int k=0;
        String s1 = null;
        ArrayList<String> stopwords = new ArrayList<>();
        while((s1 = br1.readLine()) != null){
        	
        	stopwords.add(s1);
        	k++;
        }
    	
    	
    	return stopwords;
    	
    	
    }
	
	public static ArrayList<String> threeGramBuilder(ArrayList<String> resultList) {
        ArrayList<String> threeGrams = new ArrayList<>();


        for (int i = 0; i < resultList.size() - 3 + 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < i + 3; j++)
                sb.append((j > i ? " " : "") + resultList.get(j));


            threeGrams.add(sb.toString());
        }

        return threeGrams;

    }

	
	
	 static class ValueComparator implements Comparator<String> {

	        Map<String, Integer> map;

	        public ValueComparator(Map<String, Integer> base) {
	            this.map = base;
	        }

	        public int compare(String a, String b) {
	            if (map.get(a) >= map.get(b)) {
	                return -1;
	            } else {
	                return 1;
	            }
	        }
	 }
}



