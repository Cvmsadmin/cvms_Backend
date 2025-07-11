package org.ss.vendorapi.util;


import java.util.StringTokenizer;

public class NumberToWordConversion {

	String string;
	String a[]={"",
	"One",
	"Two",
	"Three",
	"Four",
	"Five",
	"Six",
	"Seven",
	"Eight",
	"Nine",
	};

	String b[]={
	"Hundred",
	"Thousand",
	"Lakh",
	"Crore"

	};

	String c[]={"Ten",
	"Eleven",
	"Twelve",
	"Thirteen",
	"Fourteen",
	"Fifteen",
	"Sixteen",
	"Seventeen",
	"Eighteen",
	"Ninteen",
	};

	String d[]={

	"Twenty",
	"Thirty",
	"Fourty",
	"Fifty",
	"Sixty",
	"Seventy",
	"Eighty",
	"Ninty"
	};
	public String retrieveWord(String amount){
		String finalAmountWord="";
		String beforeDecimal="";
		String afterDecimal="";
		String amountString=amount.trim();
		StringTokenizer st=new StringTokenizer(amountString,".");
		while (st.hasMoreTokens()) {
			beforeDecimal=st.nextToken();
			afterDecimal=st.nextToken(".");
		}
		Integer befDecNum=new Integer(beforeDecimal);
		Integer aftDecNum=new Integer(afterDecimal);
		String befDecWord=convertNumToWord(befDecNum);
		String aftDecWord=convertNumToWord(aftDecNum);
		if (aftDecWord.trim().equalsIgnoreCase("")||aftDecWord.trim().equalsIgnoreCase("0")||aftDecWord.trim().equalsIgnoreCase("00")) {
			finalAmountWord=befDecWord+" "+"Rupees"+" "+"And"+" "+"Zero"+" "+"Paisa"+" "+"Only";
		}else {
			finalAmountWord=befDecWord+" "+"Rupees"+" "+"And"+" "+aftDecWord+" "+"Paisa"+" "+"Only";
		}		
		return finalAmountWord;		
	}

	public String convertNumToWord(int number){

		int c=1;
		int rm ;
		string="";
		while ( number != 0 )
		{
		switch ( c )
		{
		case 1 :
		rm = number % 100 ;
		pass ( rm ) ;
		/*if( number > 100 && number % 100 != 0 )
		{
		display ( "and " ) ;
		}*/
		number /= 100 ;

		break ;

		case 2 :
		rm = number % 10 ;
		if ( rm != 0 )
		{
		display ( " " ) ;
		display ( b[0] ) ;
		display ( " " ) ;
		pass ( rm ) ;
		}
		number /= 10 ;
		break ;

		case 3 :
		rm = number % 100 ;
		if ( rm != 0 )
		{
		display ( " " ) ;
		display ( b[1] ) ;
		display ( " " ) ;
		pass ( rm ) ;
		}
		number /= 100 ;
		break ;

		case 4 :
		rm = number % 100 ;
		if ( rm != 0 )
		{
		display ( " " ) ;
		display ( b[2] ) ;
		display ( " " ) ;
		pass ( rm ) ;
		}
		number /= 100 ;
		break ;

		case 5 :
		rm = number % 100 ;
		if ( rm != 0 )
		{
		display ( " " ) ;
		display ( b[3] ) ;
		display ( " " ) ;
		pass ( rm ) ;
		}
		number /= 100 ;
		break ;

		}
		c++ ;
		}

		return string;
		}

		public void pass(int number)
		{
		int rm, q ;
		if ( number < 10 )
		{
		display ( a[number] ) ;
		}

		if ( number > 9 && number < 20 )
		{
		display ( c[number-10] ) ;
		}

		if ( number > 19 )
		{
		rm = number % 10 ;
		if ( rm == 0 )
		{
		q = number / 10 ;
		display ( d[q-2] ) ;
		}
		else
		{
		q = number / 10 ;
		display ( a[rm] ) ;
		display ( " " ) ;
		display ( d[q-2] ) ;
		}
		}
		}

		public void display(String s)
		{
		String t ;
		t= string ;
		string= s ;
		string+= t ;
		}
}
