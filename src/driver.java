import java.io.*;
import java.util.Scanner;
//-----------------------------------------------------
//Assignment 3
//Written by: Evan Teboul and 40238390
//-----------------------------------------------------
/**
* Evan Teboul and 40238390
* COMP249
* Assignment 2
* March 27 2023
* @author evanteboul
*
*/

/**
 * this class runs the assignement and contains the main method and the do parts methods
 * @author evanteboul
 *
 */
public class driver {
	/**
	 * this static attribute of the driver class is used in the main method.
	 */
	final static String[] genreFiles = 
		{"src/Genres/Cartoons_Comics_Books.csv.txt",
		 "src/Genres/Hobbies_Collectibles.csv.txt",
		 "src/Genres/Movies_TV_Books.csv.txt",
		 "src/Genres/Music_Radio_Books.csv.txt",
		 "src/Genres/Nostalgia_Eclectic_Books.csv.txt",
		 "src/Genres/Old_Time_Radio_Books.csv.txt",
		 "src/Genres/Sports_Sports_Memorabilia.csv.txt",
		 "src/Genres/Trains_Planes_Automobiles.csv.txt"
		};
	/**
	 * This attribute has package access so that the validator file which holds all the methods can use it.
	 */
	final static String[] allGenreFiles= {
		 "src/Genres/Cartoons_Comics_Books.csv.txt",
		 "src/Genres/Hobbies_Collectibles.csv.txt",
		 "src/Genres/Movies_TV_Books.csv.txt",
		 "src/Genres/Music_Radio_Books.csv.txt",
		 "src/Genres/Nostalgia_Eclectic_Books.csv.txt",
		 "src/Genres/Old_Time_Radio_Books.csv.txt",
		 "src/Genres/Sports_Sports_Memorabilia.csv.txt",
		 "src/Genres/Trains_Planes_Automobiles.csv.txt",
		 "src/Genres/syntax_error_file.txt"
	};
	
	public static String removePath(String fileName) {
		return fileName.replaceAll("src/binary_files/", "");
	}
	
	/**
	 * this makes creating files easier
	 * @param genreFiles an array of the names of the files you would like to create
	 */
	public static void MakeFiles(String[] genreFiles) {
		String[] binFiles=new String[genreFiles.length];
		for(int i =0;i<genreFiles.length;i++) {
			binFiles[i] = genreFiles[i].replace("Genres","binary_files") + ".ser";
		}
		for(int i=0;i<binFiles.length;i++) {
			File file = new File(binFiles[i]);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * this is the method that will do part 1 of the assignment
	 * it essentially reads the input file, then checks for syntax errors and sorts the files by genre.
	 * files with bad syntax go in "syntax_error_file.txt".
	 * 
	 * otherwise they get sorted based on their genre code.
	 * 
	 * The full code for this method is mainly done through static methods created in the "validator" file, 
	 * the main method used for this is the validator.SyntaxErrors static method.
	 */
	public static void do_part1() {
		try {
			String[] arr = validator.readFile("src/Assg3_Needed_Files/part1_input_file_names.txt");
				for(int i = 0;i<arr.length;i++) {
					try {
						validator.SyntaxErrors("src/Assg3_Needed_Files/"+arr[i]);
					}catch(FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
		}catch(FileNotFoundException e ) {
			System.out.println(e.getMessage());
		}	
	}
	/**
	 * this method is just the SemanticErrors method from the validator class.
	 */
	public static void do_part2() {
		validator.SemanticErrors(genreFiles);
	}
	/**
	 * In this class the binary files were hard coded in and stored in a String array.
	 * Then there is a jagged 2D Book array  with the first dimension having the same length as the binaryFiles array and each second dimension has a length
	 * equivalent to the amount of valid book files in that file.
	 * what I did to place to store each Book[] into the 2D array was read each binary file, count the amount of book records inside, create a Book array with that count
	 * and then store each book in that Book[].
	 * After that I stored the 1D array into the 2D array.
	 * and this 2D array is what will be accessed through the console.
	 */
	public static void do_part3() {
			String[] binaryFiles = 
				{
				 "src/binary_files/Cartoons_Comics_Books.csv.txt.ser",
				 "src/binary_files/Hobbies_Collectibles.csv.txt.ser",
				 "src/binary_files/Movies_TV_Books.csv.txt.ser",
				 "src/binary_files/Music_Radio_Books.csv.txt.ser",
				 "src/binary_files/Nostalgia_Eclectic_Books.csv.txt.ser",
				 "src/binary_files/Old_Time_Radio_Books.csv.txt.ser",
				 "src/binary_files/Sports_Sports_Memorabilia.csv.txt.ser",
				 "src/binary_files/Trains_Planes_Automobiles.csv.txt.ser"
				};
			int count=0;
			Book[][] bookArray2D = new Book[binaryFiles.length][];
			int index2dArray=0;
			Book[] bookArray1=null;
			ObjectInputStream rd=null;
			for(int i = 0;i<binaryFiles.length;i++) {
				count=0;
				try {
					rd = new ObjectInputStream(new FileInputStream(binaryFiles[i]));
					while(true) {
						rd.readObject();
						count++;
					}
				}catch(EOFException e) {
					try {
						rd.close();
					}catch(IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//do same thing again.
				bookArray1 = new Book[count]; 
				int arrIndex =0;
				try {
					rd = new ObjectInputStream(new FileInputStream(binaryFiles[i]));
					for(int x=0;x<bookArray1.length;x++)
						bookArray1[arrIndex++] = (Book) rd.readObject();
					
				}catch(EOFException e) {
					try {
						rd.close();
					}catch(IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					bookArray2D[index2dArray++] = bookArray1;
//					System.out.println("Array index "+ (index2dArray-1) + " has length "+ bookArray2D[index2dArray-1].length);
				}
			}
				//at this point the array is created.
			Scanner keyIn = new Scanner(System.in);
			int index =0;
			while(true) {
				
				System.out.println("----------------------------");
				System.out.println("\tMain menu");
				System.out.println("----------------------------");
				System.out.println("v View the selected file: " + removePath(binaryFiles[index]) + "\t"  + "(" + bookArray2D[index].length + " records)" );
				System.out.println("s Select a file to view");
				System.out.println("x Exit");
				System.out.println("----------------------------\n\n");
				System.out.print("Enter your Choice: ");
				String letter= keyIn.next();
				
				
				if(letter.equalsIgnoreCase("s")) {
					System.out.println("----------------------------");
					System.out.println("\tFile Sub-Menu");
					System.out.println("----------------------------");
					for(int i =0;i<binaryFiles.length;i++) {
						if(i==2) {
							System.out.println((i+1) + "  " + removePath(binaryFiles[i]) + "            (" + bookArray2D[i].length +" records)");
							continue;
						}
						if(i==4) {
							System.out.println((i+1) + "  " + removePath(binaryFiles[i]) + "   (" + bookArray2D[i].length +" records)");
							continue;
						}
						if(i==6||i==7) {
							System.out.println((i+1) + "  " + removePath(binaryFiles[i]) + "  (" + bookArray2D[i].length +" records)");
							continue;
						}
							
						System.out.println((i+1) + "  " + removePath(binaryFiles[i]) + " \t  (" + bookArray2D[i].length +" records)");
					}
						
					System.out.println("9  Exit" );
					
					System.out.println("----------------------------\n\n");
					System.out.print("Enter your choice: ");
					
					int number=0;
					
					while(true) {
						String num = keyIn.next();
						try {
							number = Integer.parseInt(num);
							break;
						}catch(NumberFormatException e) {
							System.out.println("Input is not a number in the list.");
							System.out.print("Please try again: ");
						}
					}
					index = number-1;
					continue;
				}
					
				else if(letter.equalsIgnoreCase("x")) {
					System.out.println("Thank you for viewing!");
					keyIn.close();
					System.exit(0);
				}
				else if(letter.equalsIgnoreCase("v")) {
					System.out.println("viewing: " + removePath(binaryFiles[index]) + "\t(" + bookArray2D[index].length + " records)");
					int objectNumber =0;
					System.out.println(bookArray2D[index][objectNumber]);
					System.out.print("Please enter a viewing command: ");
					while(true) {
						try {
							String n0 = keyIn.next();
							int n = Integer.parseInt(n0);
//							System.out.println(n);
							int move= Math.abs(n)-1;
//							System.out.println("move by: "+move);
							if(n==0)
								break;
							else if(n>0){
								if(move+objectNumber>=bookArray2D[index].length) {
//									System.out.println(move+objectNumber);
									throw new IndexOutOfBoundsException("EOF");	
								}
									
								System.out.println(bookArray2D[index][objectNumber]);
								for(int i =0;i<move;i++) {
									System.out.println(bookArray2D[index][++objectNumber]);
								}
									
								System.out.println("Book "+(objectNumber+1) + "/" + bookArray2D[index].length);
								continue;
							}
							else if(n<0) {
								if((objectNumber-move)<0) {
//									System.out.println(objectNumber-move);
									throw new IndexOutOfBoundsException("BOF");
								}
								System.out.println(bookArray2D[index][objectNumber]);
								for(int i=0;i<move;i++) 
									System.out.println(bookArray2D[index][--objectNumber]);
								System.out.println("Book "+(objectNumber+1) + "/" + bookArray2D[index].length);
								continue;
							}
							
						}catch(NumberFormatException e) {
							System.out.print("This is not an Integer please try another viewing command: ");
						}catch(IndexOutOfBoundsException e) {
							if(e.getMessage().equals("EOF"))
							System.out.println("EOF has been reached");
							else if(e.getMessage().equals("BOF"))
								System.out.println("BOF has been reached");
							else {
								e.printStackTrace();
							}
						}
					} continue; //this skips the invalid input piece.
				}
				System.out.println("Invalid input please refer to main menu.");
				System.out.println("\n");
			}
	}
	public static void main(String[] args){
		validator.clear(allGenreFiles);
		do_part1();
		do_part2();
		do_part3();
	}
}
