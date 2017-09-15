Minim minim;
AudioOutput out;

Sampler[] samplers = new Sampler[20];

Sampler s1;
Sampler s2;
Sampler s3;
Sampler s4;
Sampler s5;
Sampler s6;
Sampler s7;
Sampler s8;
Sampler s9;
Sampler s10;
Sampler s11;
Sampler s12;
Sampler s13;
Sampler s14;
Sampler s15;
Sampler s16;
Sampler s17;
Sampler s18;
Sampler s19;
Sampler s20;
Sampler s21;

int soundTimer = 0;
int soundInterval = 1000;
int bufferSize = 256;
int voices = 4;




void loadSounds() {
  out   = minim.getLineOut();
  s1 = new Sampler( "data/your business is appreciated.wav", voices, minim);
  s2 = new Sampler( "data/who's there.wav", voices, minim);
  s3 = new Sampler( "data/there you are.wav", voices, minim);
  s4 = new Sampler( "data/there you are(2).wav", voices, minim);
  s5 = new Sampler( "data/target lost.wav", voices, minim);
  s6 = new Sampler( "data/target aquired.wav", voices, minim);
  s7 = new Sampler( "data/sleep mode activated.wav", voices, minim);
  s8 = new Sampler( "data/sentry mode activated.wav", voices, minim);
  s9 = new Sampler( "data/no hard feelings.wav", voices, minim);
  s10 = new Sampler( "data/is anyone there.wav", voices, minim);
  s11 = new Sampler( "data/i see you.wav", voices, minim);
  s12 = new Sampler( "data/i dont hate you.wav", voices, minim);
  s13 = new Sampler( "data/i dont blame you.wav", voices, minim);
  s14 = new Sampler( "data/hey its me.wav", voices, minim);
  s15 = new Sampler( "data/hello.wav", voices, minim);
  s16 = new Sampler( "data/gotcha.wav", voices, minim);
  s17 = new Sampler( "data/dispensing product.wav", voices, minim);
  s18 = new Sampler( "data/deploying.wav", voices, minim);
  s19 = new Sampler( "data/could you come over here.wav", voices, minim);
  s20 = new Sampler( "data/are you still there.wav", voices, minim);
  s21 = new Sampler( "data/activated.wav", voices, minim);

  
  //s1 = minim.loadSample("data/your business is appreciated.wav",bufferSize);
  //s2 = minim.loadSample("data/who's there.wav",bufferSize);
  //s3 = minim.loadSample("data/there you are.wav",bufferSize);
  //s4 = minim.loadSample("data/there you are(2).wav",bufferSize);
  //s5 = minim.loadSample("data/target lost.wav",bufferSize);
  //s6 = minim.loadSample("data/target aquired.wav",bufferSize);
  //s7 = minim.loadSample("data/sleep mode activated.wav",bufferSize);
  //s8 = minim.loadSample("data/sentry mode activated.wav",bufferSize);
  //s9 = minim.loadSample("data/no hard feelings.wav",bufferSize);
  //s10 = minim.loadSample("data/is anyone there.wav",bufferSize);
  //s11 = minim.loadSample("data/i see you.wav",bufferSize);
  //s12 = minim.loadSample("data/i dont hate you.wav",bufferSize);
  //s13 = minim.loadSample("data/i dont blame you.wav",bufferSize);
  //s14 = minim.loadSample("data/hey its me.wav",bufferSize);
  //s15 = minim.loadSample("data/hello.wav",bufferSize);
  //s16 = minim.loadSample("data/gotcha.wav",bufferSize);
  //s17 = minim.loadSample("data/dispensing product.wav",bufferSize);
  //s18 = minim.loadSample("data/deploying.wav",bufferSize);
  //s19 = minim.loadSample("data/could you come over here.wav",bufferSize);
  //s20 = minim.loadSample("data/are you still there.wav",bufferSize);
  //s21 = minim.loadSample("data/activated.wav",bufferSize);
}

void playSampler(Sampler s) {
  s.patch(out);
  s.trigger();
  s.unpatch(out);
}

void playSound(int sound) {
  if(soundEffects) {
    if(sound == 1) {
      playSampler(s1);
      //s1.trigger();
      //s1.play();
    }
    if(sound == 2) {
      playSampler(s2);
      //s2.trigger();
      //s2.play();
    }
    if(sound == 3) {
      playSampler(s3);
      //s3.trigger();
      //s3.play();
    }
    if(sound == 4) {
      playSampler(s4);
      //s4.trigger();
      //s4.play();
    }
    if(sound == 5) {
      playSampler(s5);
      //s5.trigger();
      //s5.play();
    }
    if(sound == 6) {
      playSampler(s6);
      //s6.trigger();
      //s6.play();
    }
    if(sound == 7) {
      playSampler(s7);
      //s7.trigger();
      //s7.play();
    }
    if(sound == 8) {
      playSampler(s8);
      //s8.trigger();
      //s8.play();
    }
    if(sound == 9) {
      playSampler(s9);
      //s9.trigger();
      //s9.play();
    }
    if(sound == 10) {
      playSampler(s10);
      //s10.trigger();
      //s10.play();
    }
    if(sound == 11) {
      playSampler(s11);
      //s11.trigger();
      //s11.play();
    }
    if(sound == 12) {
      playSampler(s12);
      //s12.trigger();
      //s12.play();
    }
    if(sound == 13) {
      playSampler(s13);
      //s13.trigger();
      //s13.play();
    }
    if(sound == 14) {
      playSampler(s14);
      //s14.trigger();
      //s14.play();
    }
    if(sound == 15) {
      playSampler(s15);
      //s15.trigger();
      //s15.play();
    }
    if(sound == 16) {
      playSampler(s16);
      //s16.trigger();
      //s16.play();
    }
    if(sound == 17) {
      playSampler(s17);
      //s17.trigger();
      //s17.play();
    }
    if(sound == 18) {
      playSampler(s18);
      //s18.trigger();
      //s18.play();
    }
    if(sound == 19) {
      playSampler(s19);
      //s19.trigger();
      //s19.play();
    }
    if(sound == 20) {
      playSampler(s20);
      //s20.trigger();
      //s20.play();
    }
    if(sound == 21) {
      playSampler(s21);
      //s21.trigger();
      //s21.play();
    }
  }
}

void randomIdleSound() {
  if(soundEffects) {
    int sound = int(random(1, 11));
    if(sound == 1) {
      s2.trigger();
      //s2.play();
    }
    if(sound == 2) {
      s7.trigger();
      //s7.play();
    }
    if(sound == 3) {
      s9.trigger();
      //s9.play();
    }
    if(sound == 4) {
      s10.trigger();
      //s10.play();
    }
    if(sound == 5) {
      s11.trigger();
      //s11.play();
    }
    if(sound == 6) {
      s12.trigger();
      //s12.play();
    }
    if(sound == 7) {
      s13.trigger();
      //s13.play();
    }
    if(sound == 8) {
      s14.trigger();
      //s14.play();
    }
    if(sound == 9) {
      s19.trigger();
      //s19.play();
    }
    if(sound == 10) {
      s20.trigger();
      //s20.play();
    }
  }
}