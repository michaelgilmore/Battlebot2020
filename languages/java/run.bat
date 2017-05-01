::For usage printout
::java cc.gilmore.athenahealth.AthenahealthCodingTest

::*******************************************************************
::System.out.println("TestA - Expected Answer: 2");
::source = "*-_-***"; //AB
::hiddenMsg1 = "*-*"; //R
java cc.gilmore.athenahealth.AthenahealthCodingTest -testA

::*******************************************************************
::System.out.println("TestB - Expected Answer: 1311");
::source = "****_*_*-**_*-**_---____*--_---_*-*_*-**_-**"; //Hello World
::hiddenMsg1 = "****_*_*-**_*--*"; //Help
::java cc.gilmore.athenahealth.AthenahealthCodingTest -testB

::*******************************************************************
::System.out.println("TestC - Expected Answer: 5");
::source = "*-_-***_-*-*_-**"; //ABCD
::hiddenMsg1 = "***_-"; //ST
::hiddenMsg2 = "--**_-*"; //ZN
::java cc.gilmore.athenahealth.AthenahealthCodingTest -testC

::*******************************************************************
::System.out.println("TestD - Expected Answer: 2");
:://This is one from Step Two of TestC.
::source = "-_-**-*_-**"; //
::hiddenMsg1 = "--**_-*"; //ZN
::java cc.gilmore.athenahealth.AthenahealthCodingTest -testD

::*******************************************************************
::System.out.println("TestE - Expected Answer: 11474");
::source = "-_****_*___***_-_*-_*-*___*--_*-_*-*_***___***_*-_--*_*-"; //The Star Wars Saga
::hiddenMsg1 = "-*--_---_-**_*-"; //Yoda
::hiddenMsg2 = "*-**_*_**_*-"; //Leia
::java cc.gilmore.athenahealth.AthenahealthCodingTest -testE
