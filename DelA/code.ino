int RedLed=13;
int YellowLed=12;
int GreenLed=11;
int val=0;
int Pir=2;
int code[] = {1,2};
int button1=0;
int button2=0;

 
 
 

void setup()
{
  Serial.begin(9600);
 
  pinMode(RedLed, OUTPUT);
  pinMode(YellowLed, OUTPUT);
  pinMode(GreenLed, OUTPUT);
  
  pinMode(6, INPUT);
  pinMode(7, INPUT);
  pinMode(Pir, INPUT);
  
  //blink(RedLed);
  //blink(YellowLed);
  //blink(GreenLed);
  
  for(int i=0;i<4;i++){
    setleds(HIGH,HIGH,HIGH);
    delay(500);
    setleds(LOW,LOW,LOW);
    delay(500);
    
  }
  
 setleds(HIGH,LOW,LOW);
  printstate();
    
  
 

}

const int LOCKED=1;
const int UNLOCKED=5;
const int CHECKING=4;
const int WAITING1=2;
const int WAITING2=3;
int status = LOCKED;
void printstate() {

		switch (status) {
		case LOCKED:
			Serial.println("The system is locked");
			break;

		case WAITING1:
			Serial.println("The system is waiting ");
			break;

		case WAITING2:
			Serial.println("The sytem is waiting");
			break;

		case CHECKING:
			Serial.println("The system is cheking");
			break;

		case UNLOCKED:
			Serial.println("The system is unlocked");
			break;

		default:
			Serial.println("ILLEGAL STATE");
			break;
		}
	}
  
  void blink(int led) {
    
    for (int i=0;i<4;i++){
      
      digitalWrite(led,LOW);
      delay(300);
      digitalWrite(led,HIGH);
      delay(300);
    
    }
  }
 

void loop()
{
  	val=digitalRead(Pir);
  	int Rightbtn=digitalRead(7);
	int Leftbtn=digitalRead(6);
  
  switch(status){
  
  	case LOCKED:
    setleds(HIGH,LOW,LOW);
    if(val==HIGH){
    status=WAITING1;
   setleds(HIGH,LOW,LOW);
    
      }
    break;
    
    case WAITING1:
    if(Leftbtn==HIGH || Rightbtn==HIGH){
    	blink(12);
       printstate();
          if(Leftbtn==HIGH){
          button1=1;
        	}
      if(Rightbtn==HIGH){
         button1=2;
      }
      
      status=WAITING2;
     
    }
     break;
       
     case WAITING2:
       if(Leftbtn==HIGH || Rightbtn==HIGH){
    	blink(12);
         printstate();
         if (Leftbtn==HIGH){
         button2=1;
         
         }
         if(Rightbtn==HIGH){
           button2=2;
         }
         status=CHECKING;
          }
    	break;
                        
    case CHECKING:
    if( button1 == code[0] && button2 == code[1]){
     status=UNLOCKED; 
         
    } else{
      blink(12);
      setleds(HIGH, LOW, LOW);
      status=LOCKED;
    }
     button1=0;
   	 button2=0;
        break;
         
   case UNLOCKED:
    blink(11);
    setleds(LOW, LOW,HIGH);
    printstate();
       delay(5000);
       status = LOCKED;
    	printstate();
        break; 
    
    default:
	break;

}
}                      
  


 void setleds(int vred, int vyellow, int vgreen) {
  digitalWrite(RedLed, vred);
  digitalWrite(YellowLed, vyellow);
  digitalWrite(GreenLed, vgreen);

	}