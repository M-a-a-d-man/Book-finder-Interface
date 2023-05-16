import java.util.*;
import java.io.*;
import errors.*;


//-----------------------------------------------------
//Assignment 3
//Written by: Evan Teboul and 40238390
//-----------------------------------------------------
/**
* Evan Teboul and 40238390
* COMP249
* Assignment 3
* March 27 2023
* Question: part1 and 2
* @author evanteboul
* The DO part 1 methods, needs to read the input text file, then read each individual file.
* Then check if each book record is syntactically correct, if it is then it will be put in the corresponding genre
*/


public class validator {
	
	
		
		public static String shortenName(String input) {
			return input.replaceAll("src/Genres/", "");
		}
		
	
	
	
	/**
	 * 
	 * @param x the isbn either 10 digit or 13 digit
	 * @throws BadIsbn10Exception
	 * @throws BadIsbn13Exception
	 */
	public static void isIsbnValid(String x) throws BadIsbn10Exception,BadIsbn13Exception {
//		String isbn = book.isbn;
		if(x.length()==10) {
			int[] digits = new int[10];
			int coefficient = 10;
			int sum=0;
			for(int i =0;i<10;i++) {
				digits[i] =  (int) x.charAt(i) ;
				sum+= (digits[i]*coefficient--);
			}
			if(sum%11 != 0) 
				throw new BadIsbn10Exception("Invalid ISNB-10");
		}
		
		else if(x.length()==13) {
			int[] digits = new int[13];
			int sum=0;
			for(int i =0;i<10;i++) {
				digits[i] =  (int) x.charAt(i);
				if(i%2==0)
					sum+= 3*digits[i];
				else 
					sum+=digits[i];
			}
			if(sum%10 != 0)
				throw new BadIsbn13Exception("Invalid ISNB-13");
		
		}
	
	}
	// Order: title, authors, price, isbn,genre, year
	/**
	 * This method checks for semantic errors and throws one of the following exceptions if it is wrong.
	 * @param arr string array that stores the information of the book record.
	 * @throws BadIsbn13Exception if the isbn is of length 13 but does not correspond to the correct format, then this is thrown.
	 * @throws BadIsbn10Exception if the isbn is of length 10 but does not correspond to the correct format, then this is thrown.
	 * @throws BadPriceException if invalid price (price is negative) then this is thrown.
	 * @throws BadYearException if invalid year (year not in range [1995,2010]) then this is thrown
	 */
	
	public static void CheckSemanticErrors(String[] arr) throws BadPriceException,BadYearException, BadIsbn10Exception, BadIsbn13Exception{
		arr.toString();
		int year = Integer.parseInt(arr[5]);
		
		if(Double.parseDouble(arr[2])<0) {
//			System.out.println(validator.printBook(arr));
			throw new BadPriceException("Invalid price");
		}
		
		
		if(year<1995||year>2010) {
//			System.out.println(validator.printBook(arr));
			throw new BadYearException("Invalid year");
		}
			
		else {
			isIsbnValid(arr[3]);
		}
		
	}
	

	
	
	
	/**
	 * Checks the syntax of the book record.
	 * @param arr the array that stores the information of each book record. It is a string array. 
	 * @throws MissingFieldException if the String array has length 6 but one of its elements are empty.
	 * @throws TooFewFieldsException if the string array has length smaller than 6 this Exception is thrown
	 * @throws TooManyFieldsException if the String array has length greater than 6 this exception is thrown.
	 * @throws UnknownGenreException the genre code (String element at index 4) does not correspond to a known genre code.
	 */
	
	
	public static void checkSyntax(String[] arr) throws MissingFieldException,TooFewFieldsException,TooManyFieldsException,UnknownGenreException {
		String field=null;
		
		if(arr.length>6) {
//			System.out.println("too many fields");
//			System.out.println(arr.length);
			throw new TooManyFieldsException();
		}
		if(arr.length<6) {
			throw new TooFewFieldsException();
		}
		else if(arr.length==6) {
			for(int i = 0;i<arr.length;i++) { //checks for missing fields
				if(arr[i].equals("")) {
					switch(i) {
					case 0:
						field="title";
						break;
					case 1:
						field="authors";
						break;
					case 2:
						field="price";
						break;
					case 3:
						field="isbn";
						break;
					case 4:
						field="genre";
						 break;
					case 5: 
						field="year";
						break;
					}
					throw new MissingFieldException("missing "+ field);
				}
			}
			// Order: title, authors, price, isbn,genre, year
			String genre = arr[4];
			if(genre.equals("CCB")||genre.equals("HCB")||genre.equals("MTV")||genre.equals("MRB")||
			   genre.equals("NEB")|| genre.equals("OTR")|| genre.equals("SSM")||genre.equals("TPA")){
//			System.out.println("known genre");
			}
			else {
//				System.out.println(validator.printBook(arr));
//				System.out.println("unknown genre");
				throw new UnknownGenreException("Unknown genre");
			}
//			System.out.println(validator.printBook(arr));	
				
		}
//		return true;
	}
	
	public static String printBook(String[] arr) {
		String str="";
		for(int i =0;i<arr.length;i++) {
			if(i==arr.length-1)
				str+=arr[i];
			else
			str+= arr[i] +",";
		}
		return str;
			
	}
	
	
	//catch order: any
	/**
	 * This will combine many ,methods in this file, to sort the books into the proper files. Which will be used in the do part 1 method in the driver.
	 * Meant to be used on each file that has book records in it. Will access a file and validate all the book records.
	 * @param FileName is the name of the file 
	 * @throws FileNotFoundException this can be thrown if the file is not found, the reason why it was not caught in this method is because we want to be able to skip over any files that are not found instead of
	 * disrupting the flow of the function.
	 
	 */
	public static void SyntaxErrors(String FileName) throws FileNotFoundException{
		Scanner rd = retrieveFile(FileName);
		String shortName =FileName;
		
		for(int i =0;i<3;i++)
			shortName = shortName.substring(shortName.indexOf("/")+1);
		
		
		String [] arr = null;
		PrintWriter wrt=null; //for writing to error files
		PrintWriter genre = null;
//		int count =0;
		try{
			wrt = new PrintWriter(new FileOutputStream("src/Genres/syntax_error_file.txt",true));
		}catch(FileNotFoundException e) {
			System.out.println("Could not open output file for writing."
					+ " Please check if file exists.");	
			System.out.print("Program will terminate.");
			System.exit(0);
		}
		
		while(rd.hasNextLine()) { //checks each full file
			arr= validator.readBookFile(rd);
			
			
			try {
				checkSyntax(arr);
				//code for placing the file into a .csv.txt
				switch(arr[4]) {
				case "CCB":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Cartoons_Comics_Books.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
				case "HCB":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Hobbies_Collectibles.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
				case "MTV":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Movies_TV_Books.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
					
				case "MRB":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Music_Radio_Books.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
					
					
				case "NEB":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Nostalgia_Eclectic_Books.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
				case "OTR":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Old_Time_Radio_Books.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
				}
					
				
				case "SSM":{
					genre = new PrintWriter(new FileOutputStream("src/Genres/Sports_Sports_Memorabilia.csv.txt",true));
					genre.write(validator.printBook(arr)+ "\n");
					genre.close();
					break;
					
				}
					
					
				case "TPA":{
					
					genre = new PrintWriter(new FileOutputStream("src/Genres/Trains_Planes_Automobiles.csv.txt",true));
					genre.write(validator.printBook(arr) + "\n");
					genre.close();
					break;
				}default:{
					throw new UnknownGenreException("unknown genre");
				}
				}
			}catch(FileNotFoundException e) {
					System.out.println("file not found exception");	
			}catch(MissingFieldException e) {
				wrt.println("syntax error in file: " +shortName);
				wrt.println("====================");
				wrt.println("Error: " + e.getMessage() );
				wrt.println("Record: " + printBook(arr) +"\n");
			}catch(TooManyFieldsException e) {
				wrt.println("syntax error in file: " +shortName);
				wrt.println("====================");
				wrt.println("Error: " + e.getMessage());
				wrt.println("Record: " + printBook(arr) +"\n");
			}catch(TooFewFieldsException e) {
				wrt.println("syntax error in file: " +shortName);
				wrt.println("====================");
				wrt.println("Error: " + e.getMessage());
				wrt.println("Record: " + printBook(arr) +"\n");
			}catch(UnknownGenreException e) {
				wrt.println("syntax error in file: " +shortName);
				wrt.println("====================");
				wrt.println("Error: " + e.getMessage());
				wrt.println("Record: " + printBook(arr) +"\n");
			}
		}
		
		rd.close();
		wrt.close();
	}
	

	
	// Order: title, authors, price, isbn,genre, year
	
	
	/**
	 * This method runs a little differently than the Syntax error method. Since this method reads the already sorted books.
	 * This method works alone, this is the do_part2 method. The way that this works is that it reads each book category file, and scans for errors.
	 * and it counts how many files are semantically correct, and writes all the incorrect ones to the file named "semantic_error_file.txt".
	 * Then after reading the entire file it uses the count variable to create an array of Book objects that has the same size as there are correct book records.
	 * Then the the same file is read a second time and the correct files are transferred to Book objects and stored in the Book object array.
	 * Then after the array is filled, the elements are then written to a file for that category.
	 * @param arr this is an array that holds the names of the files we want to read, serialize and write to a new file.  
	 */
	
	public static void SemanticErrors(String[] arr) {
		String outputName="";
		String shortName=""; //will be used to express the file name that contains the book with the semantic error.
		Scanner rd=null;
//		ObjectOutputStream genre =null; //this is for writing to the binary file
		PrintWriter wrt = null; //this if for writing to the semantic error file
		String[] book =null; //this is the book record information
		Book[] bookArray =null;
//		ArrayList<Book> bookObjectArray = new ArrayList<Book>(); //used this to be able to constantly change the size of the array
		int count =0;
		try {
			wrt= new PrintWriter(new FileOutputStream("src/binary_files/semantic_error_file.txt"));
		
		
		}catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		
		try {
			for(int i =0;i<arr.length;i++) {
				count=0;
				shortName= shortenName(arr[i]);
				outputName= "src/binary_files/" + shortName+ ".ser";
				rd = new Scanner(new FileInputStream(arr[i]));
//				ArrayList<Book> bookObjectArray = new ArrayList<Book>();
				
				while(rd.hasNextLine()) {
					
					book = readBookFile(rd); //scans the line
					try {
//						for(int x =0;x<book.length;x++)
//							System.out.println(book[x]);
						CheckSemanticErrors(book); //checks for semantic error
						count++;
//						bookObject= Book.toBook(book); //transfers the String to a book object
//						bookObjectArray.add(bookObject);//takes the BookObject Array and increases its size and add the book to it.						
					}catch(BadPriceException e) {
//						System.out.println(validator.printBook(book));
						wrt.println("Semantic error in file: " + shortName);
						wrt.println("====================");
						wrt.println("Error: " + e.getMessage() );
						wrt.println("Record: " + printBook(book) + "\n");
					}catch(BadIsbn10Exception e) {
//						System.out.println(validator.printBook(book));
						wrt.println("Semantic error in file: " + shortName);
						wrt.println("====================");
						wrt.println("Error: " + e.getMessage() );
						wrt.println("Record: " + printBook(book) +"\n");
					}catch(BadIsbn13Exception e) {
//						System.out.println(validator.printBook(book));
						wrt.println("Semantic error in file: " + shortName);
						wrt.println("====================");
						wrt.println("Error: " + e.getMessage() );
						wrt.println("Record: " + printBook(book) +"\n");
					}catch(BadYearException e) {
//						System.out.println(validator.printBook(book));
						wrt.println("Semantic error in file: " + shortName);
						wrt.println("====================");
						wrt.println("Error: " + e.getMessage() );
						wrt.println("Record: " + printBook(book) +"\n");
					}finally {
						wrt.flush();
					}
				}
				rd.close();
				rd = new Scanner(new FileInputStream(arr[i]));
				bookArray = new Book[count];
				int index=0;
				Book record = null;
				while(rd.hasNextLine()) {
					book = readBookFile(rd);
					try {
						CheckSemanticErrors(book);
						record = Book.toBook(book);
						bookArray[index++] = record;
						
					}catch(BadPriceException e) {
					}catch(BadIsbn10Exception e) {
					}catch(BadIsbn13Exception e) {
					}catch(BadYearException e) {
					}
				}
				rd.close();
				ObjectOutputStream binFile= new ObjectOutputStream(new FileOutputStream(outputName));
//				System.out.println(bookArray.length);
					for(int x=0;x<bookArray.length;x++) {
						binFile.writeObject(bookArray[x]);
//						binFile.flush();
					}
					binFile.close();
			}
			wrt.close();
			}catch(FileNotFoundException e) {
			System.out.println("File " + outputName   +" was not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method reduces redundant code since it allows for a scanner to be made to read the file. 
	 * @param fileName this is the file we want to read
	 * @return Scanner  this is what will be used to read files.
	 * @throws FileNotFoundException if a file is not found this exception is thrown.
	 */
	public static Scanner retrieveFile(String fileName) throws FileNotFoundException{
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new FileInputStream(fileName));
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException("Could not open input file, " + fileName+ " for reading."+ " Please check if file exists.");	
		}catch(NoSuchElementException e) {
			System.out.println("This file is empty");
		}
		return inputFile;	
	}
	
	/**
	 * This method read the part1_input_file_names.txt file and places each book file into an array for easy access in the do_part1 method.
	 * @param name this is supposed to be the file name of the file that holds the files books1995.csv.txt to books2010.csv.txt
	 * @return
	 * @throws FileNotFoundException
	 */
	
	public static String[] readFile(String name) throws FileNotFoundException {
		Scanner file = retrieveFile(name);
		
		int amountOfBooks = file.nextInt(); //this will read the first line
		String[] bookFileArray = new String[amountOfBooks];
		
		int count=0;
		String junk = file.nextLine(); //need this 
		while(file.hasNextLine()) {
			bookFileArray[count++] = file.nextLine() ; //will read until a line seperator is found, will read each line since they're separated by \n
		}
		return bookFileArray;
	}
	// Order: title, authors, price, isbn,genre, year
	
	/**
	 * This method reads a line and places each piece of information in an array.
	 * The pieces of information stored should be in the 
	 * Order: title, authors, price, isbn,genre, year
	 * @param x scanner so that this method can read a file
	 * @return String[] this will be an array holding a book record, the book information is split at each comma
	 */
	
	public static String[] readBookFile(Scanner x) {
		String[] book;
			String line = x.nextLine();
//			System.out.println(line);
			String title;
			String[] arr;
//			System.out.println(line.indexOf("\""));
			if(line.indexOf("\"")==-1) { //if the quotes are missing array is still made.
				book = line.split(",");
//				System.out.println("missing quotes");
//				System.out.println(book.length);
			}
			else{
//				System.out.println(line.indexOf("\""));
				line = line.substring(line.indexOf("\"")+1); //cuts out the first doubleQuote
				title = "\""+ line.substring(0,line.indexOf("\"")) +"\""; //grabs the title, adds quotes back
				line = line.substring(line.indexOf("\"")+2);//removes the first comma after title
				arr = line.split(","); //splits the array at the comma
				book=new String[arr.length+1];
				book[0]=title; //places the title in the string
				book[0]=title;
				for(int i=0;i<arr.length;i++) 
					book[i+1]=arr[i];
			}
		return book;
	}
	/**
	 * This method takes a scanner and reads a single line, it is meant to be placed in a loop so many lines can be read.
	 * @param x this is the scanner that will be used to read the contents of each file   
	 * @return returns the string array of a bookArray for each line in a bookFile
	 */
	
	public static void clear(String[] arr) {
		PrintWriter wrt=null;
		try {
			for(int i =0;i<arr.length;i++) {
				wrt= new PrintWriter(new FileOutputStream(arr[i]));
				wrt.print("");
				wrt.close();
			}
			
		}catch(FileNotFoundException e) {
			
		}
	}	
	
	/**
	 * this is the same method as the clear() except it clears the serialized files.
	 * this function facilitates testing.
	 * @param arr is an array of the names of the binary files the user would like to clear.
	 */
	public static void clearBin(String[] arr) {
		ObjectOutputStream wrt=null;
		try {
			for(int i =0;i<arr.length;i++) {
				wrt= new ObjectOutputStream(new FileOutputStream(arr[i]));
				wrt.writeUTF("");
				wrt.close();
			}
			
		}catch(IOException e) {
			
		}
	}	
	
	/**
	 * this method is meant for testing it essentially prints the file that it has read and the amount of files within it.
	 */
	public static void read(){
		String[] genreFiles = 
			{
			 "src/Genres/Cartoons_Comics_Books.csv.txt",
			 "src/Genres/Hobbies_Collectibles.csv.txt",
			 "src/Genres/Movies_TV_Books.csv.txt",
			 "src/Genres/Music_Radio_Books.csv.txt",
			 "src/Genres/Nostalgia_Eclectic_Books.csv.txt",
			 "src/Genres/Old_Time_Radio_Books.csv.txt",
			 "src/Genres/Sports_Sports_Memorabilia.csv.txt",
			 "src/Genres/Trains_Planes_Automobiles.csv.txt"
//			 "src/Genres/syntax_error_file.txt"
			};
//		int count=0;
//		for(int i =0;i<genreFiles.length;i++) {
//			genreFiles[i]= "src/binary_files/" + shortenName(genreFiles[i])+".ser";
//		}
//		clearBin(genreFiles);
//		driver.do_part2();
		int count=0;
			int index = 0;
			Book book=null;
			Scanner rd=null;
			for(int i = 0;i<genreFiles.length;i++) {
				count=0;
				try {
					rd = new Scanner(new FileInputStream(genreFiles[i]));
					while(rd.hasNextLine()) {
						rd.nextLine();
//						book.toString();
						count++;
					}
				}catch(FileNotFoundException e) {
				}finally {
					System.out.println(genreFiles[i] +": " +count);
				}
			
				
			}
	}
	
	public static void main(String[] args) {
		read();
	}
}




	
	


