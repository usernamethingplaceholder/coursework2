// jshell -J-ea --execution local "$(FULL_CURRENT_PATH)"

// creating the dictionaries:

Map<String, Integer> suitDict = new HashMap<>();
suitDict.put("H", 1); // Hearts
suitDict.put("D", 2); // Diamonds
suitDict.put("C", 3); // Clubs
suitDict.put("S", 4); // Spades

Map<String, Integer> numDict = new HashMap<>();
// default formatting
numDict.put("A", 1); // Ace
numDict.put("2", 2);
numDict.put("3", 3);
numDict.put("4", 4);
numDict.put("5", 5);
numDict.put("6", 6);
numDict.put("7", 7);
numDict.put("8", 8);
numDict.put("9", 9);
numDict.put("10", 10);
numDict.put("J", 11); // Jack
numDict.put("Q", 12); // Queen
numDict.put("K", 13); // King

// coverage for alternative formating
numDict.put("1", 1); // Jack but the files sometimes put as 1
numDict.put("11", 11); // Jack but the files sometimes put as 11
numDict.put("12", 12); // Queen but the files sometimes put as 12
numDict.put("13", 13); // King but the files sometimes put as 13


// card compare

int cardCompare(String card1, String card2) {
	// 1 if card1 > card2	LEFT LARGER
	// -1 if card1 < card2	RIGHT LARGER
	// 0 if card1 == card2	SAME
	
	// some cards are 3 length (e.g. 10S) and some are 2 (e.g. 5H)
	// so I need to use length in the way i substring it to not mess it up
	// also it's more efficient to not re-call the dictionary too many times so just here
	int card1Length = card1.length();
    int card2Length = card2.length();

    int suit1 = suitDict.get( card1.substring(card1Length - 1) ); // last char then dict
    int suit2 = suitDict.get( card2.substring(card2Length - 1) ); // last char then dict

	// compare suits first
	if (suit1 > suit2) {	
		return 1;	
    } 
	else if (suit1 < suit2) {
		return -1;
    }
		
	// then compare nums 
	
	// here bc it's more efficient to not do it if i don't need to 
	int num1 = numDict.get( card1.substring(0, card1Length - 1) ); // non last then dict
	int num2 = numDict.get( card2.substring(0, card2Length - 1) ); // non last then dict
	
	if (num1 > num2) {
        return 1;
    } 
	else if (num1 < num2) {
        return -1;
    } 
	else {
        return 0; // Both cards are equal
	}
}


// bubble sort 

ArrayList<String> bubbleSort(ArrayList<String> array){
	int n = array.size();
	boolean swapped = false;
	String temp = "";
	
	for (int i = 0; i < n; i++) {	// one for each loop
		swapped = false;
		for (int j = 0; j < n-(1 + i); j++) {	// one for each item
			if (cardCompare(array.get(j), array.get(j + 1)) > 0) {	// swaps if out of order
				temp = array.get(j);
				array.set(j, array.get(j + 1));
				array.set(j + 1, temp);
				swapped = true;
			}
		}
		if (!swapped) break;
	}
	return array;
}


// merge sort

ArrayList<String> mergeSort(ArrayList<String> array){
	if (array.size() <= 1) {
		return array;	// if size 1 then (sorted) then return as is
	}
	
	// Split into 2 halves
	int mid = array.size() / 2;	// don't need to round as it's an int
	ArrayList<String> leftArray = new ArrayList<String>(array.subList(0, mid));
	ArrayList<String> rightArray = new ArrayList<String>(array.subList(mid,array.size()));
	
	// split those halves in halves (recursively)
	leftArray = mergeSort(leftArray);
	rightArray = mergeSort(rightArray);
	
	return merge(leftArray, rightArray);	// recursively merge back up and sort
}

ArrayList<String> merge(ArrayList<String> leftArray, ArrayList<String> rightArray){
	ArrayList<String> mergedArray = new ArrayList<String>();	

	int leftIndex = 0;
	int rightIndex = 0;
	// we don't need mergedIndex because .add gets right index anyway
	
	while ( (leftIndex < leftArray.size()) && (rightIndex < rightArray.size()) ) {	
		if ( cardCompare(leftArray.get(leftIndex), rightArray.get(rightIndex)) < 0) {	// if left smaller
			mergedArray.add(leftArray.get(leftIndex)); 
			leftIndex++;
		}
		else {	// right is smaller/ same
			mergedArray.add(rightArray.get(rightIndex)); 
			rightIndex++;
		}
	}
	
	while (leftIndex < leftArray.size())
 {	// if only remaining in left
		mergedArray.add(leftArray.get(leftIndex)); 
		leftIndex++;
	}
		
	while (rightIndex < rightArray.size()) {	// if only remaining in right
		mergedArray.add(rightArray.get(rightIndex)); 
		rightIndex++;
	}	
	
	return mergedArray;
}


//	measure bubble sort

long measureBubbleSort(String filename) throws IOException{
	// get file and create and fill
	// it's coded with this much contingency so hopefully it works for both my pc and the virtual machine
	
	ArrayList<String> fileArray;
	
	try {
		// first it tries reading from its default directory
		fileArray = new ArrayList<>(Files.readAllLines(Paths.get(filename)));
	} 
	catch (IOException e) {
		try {
			// if that fails it goes to the directory i provide
			fileArray = new ArrayList<>(Files.readAllLines (Paths.get("D:/Reading/Year 1/Iterative programming/coursework2/coursework2/coursework2_files/", filename) ));
		} 
		catch (IOException ex) {
			// if both fail it throws an error
			throw new IOException("Failed to read file from both locations.", ex);
		}
	}
	
	// timings and call function
	long startTime = System.currentTimeMillis();
	bubbleSort(fileArray);
	long endTime = System.currentTimeMillis();
	
	return endTime - startTime;	// duration in ms
}


//	measure merge sort

long measureMergeSort(String filename) throws IOException{
	// get file and create and fill
	// it's coded with this much contingency so hopefully it works for both my pc and the virtual machine
	
	ArrayList<String> fileArray;
	
	try {
		// first it tries reading from its default directory
		fileArray = new ArrayList<>(Files.readAllLines(Paths.get(filename)));
	} 
	catch (IOException e) {
		try {
			// if that fails it goes to the directory i provide
			fileArray = new ArrayList<>(Files.readAllLines (Paths.get("D:/Reading/Year 1/Iterative programming/coursework2/coursework2/coursework2_files/", filename) ));
		} 
		catch (IOException ex) {
			// if both fail it throws an error
			throw new IOException("Failed to read file from both locations.", ex);
		}
	}
	
	// timings and call function
	long startTime = System.currentTimeMillis();
	mergeSort(fileArray);
	long endTime = System.currentTimeMillis();
	
	return endTime - startTime;	// duration in ms
}


// sort comparison

void sortComparison(String[] filenames) throws IOException{
    StringBuilder result = new StringBuilder();    // stringbuilders are more efficent here than strings
    // builders are similar, but instead of += you do .append and similar
    
    // dynamic header 
    for (String fileName : filenames) {
        String size = fileName.replaceAll("\\D+", ""); // removes all non digit chars
        result.append(", ").append(size);    // you can double stack appends (very cool)
    }
    result.append("\n");
    
    // bubble sort
    result.append("bubbleSort");
    for (String fileName : filenames) {
        result.append(", ").append(measureBubbleSort(fileName));
    }
    result.append("\n");
    
    // merge sort
    result.append("mergeSort");
    for (String fileName : filenames) {
        result.append(", ").append(measureMergeSort(fileName));        
    }
    result.append("\n");
    
    // save to file
    // it's coded with this much contingency so hopefully it works for both my pc and the virtual machine
    try {    
        // first off tries with writing to own directory 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sortComparison.csv"))) {
            writer.write(result.toString());
        }
    } 
    catch (IOException e) {
        // then if that doesnt work it goes to the one ive provided
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Reading/Year 1/Iterative programming/coursework2/coursework2/coursework2_files/sortComparison.csv"))) {
            writer.write(result.toString());
        }
    }
}


/*
var list = new ArrayList<String>(List.of("4H", "3S", "7S", "8C", "2D", "3H"));
bubbleSort(list);
System.out.println(list);

var list = new ArrayList<String>(List.of("4H", "3S", "7S", "8C", "2D", "3H"));
mergeSort(list);
System.out.println(list);

System.out.println( measureBubbleSort("sort10000.txt") );

System.out.println( measureMergeSort("sort10000.txt") );

sortComparison(new String[]{"sort10.txt", "sort100.txt", "sort10000.txt"});
*/