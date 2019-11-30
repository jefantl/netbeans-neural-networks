
void setup() {
  size(600, 600);
}



void draw() {
  myBoard.update();
}


void mousePressed() {
  myBoard.place((int)(((float)(mouseX)/(float)width) *3), (int)(((float)(mouseY)/(float)height) *3), 1); 
  if (myBoard.findwin() != 0) {
    myBoard.reset();
  }
}






class genePool {

  Board myBoard = new Board();
  Agent[] myNets; //actually agents

  genePool(int popSize) {
    myNets = new Agent[popSize];
  }

  void updatFitness() {
    for (int i = 0; i < myNets.length - 1; i++) {
      for (int j = i + 1; j < myNets.length; j++) {
        pitNets(myNets[i], myNets[j]);
      }
    }
  }

  void pitNets(Agent in1, Agent in2) {
    myBoard.reset();
    while (myBoard.findwin() == 0) {
      int attempt = 0;
      int[] toPlace = in1.decide(myBoard);
      int x = toPlace[attempt]%3;
      int y = toPlace[attempt]/3;
      while (myBoard.place(x, y, 1)) attempt++;
      attempt = 0;
      toPlace = in2.decide(myBoard);
      x = toPlace[attempt]%3;
      y = toPlace[attempt]/3;
      while (myBoard.place(x, y, 2)) attempt++;
    }
    if (myBoard.findwin() == 1) in1.fitness++;
    else if (myBoard.findwin() == 2) in2.fitness++;
  }
}

class Agent {

  int brain; //nueral net
  int fitness = 0;

  Agent() {
    brain = 999; //new net
  }

  Agent(int inBrain) {
    brain = inBrain;
  }

  int[] decide(Board inB) {
    int[] brainOuts = {};
    //int[9] indexs = brainOuts.sorted.indexs
    //needs to return sorted array of indexs to place
    return brainOuts;
  }
}
