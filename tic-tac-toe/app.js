var spaces = document.querySelectorAll(".board-space")
const xScore_span = document.getElementById("x-score");
const oScore_span = document.getElementById("o-score");

var turn = 0;
var xScore = 0;
var oScore = 0;

clickSpace = function(ele) {
  if (turn == 0) {
    ele.innerHTML = "X";
    turn = 1;
  } else if (turn == 1) {
    ele.innerHTML = "O";
    turn = 0;
  }

  checkForWinner(turn);
}


for (i = 0; i < spaces.length; i++) {
  spaces[i].addEventListener("click", function() {
    clickSpace(this);
  })
}

checkForWinner = function(turn) {

  //horizontals
  for (i = 0; i < 3; i++) {
    var letter = spaces[3 * i].innerHTML;
    var valid = true;
    for (j = 0; j < 3; j++) {
      if (spaces[3*i + j].innerHTML != letter){
        valid = false;
      }
    }
    if (letter != "-" && valid == true){
      incrementScore(turn);
      clearBoard();
    }
  }

  //verticals
  for (i = 0; i < 3; i++) {
    var letter = spaces[i].innerHTML;
    var valid = true;
    for (j = 0; j < 3; j++) {
      if (spaces[i + 3*j].innerHTML != letter){
        valid = false;
      }
    }
    if (letter != "-" && valid == true){
      console.log("Winner!");
      incrementScore(turn);
      clearBoard();
    }
  }

    //Diagonals
    var letter = spaces[0].innerHTML;
    var valid = true;
    for (i = 0; i < 9; i += 4) {
      if (spaces[i].innerHTML != letter){
        valid = false;
      }
    }
    if (letter != "-" && valid == true){
      console.log("Winner!");
      incrementScore(turn);
      clearBoard();
    }

    var letter = spaces[2].innerHTML;
    var valid = true;
    for (i = 2; i <= 6; i += 2) {
      if (spaces[i].innerHTML != letter){
        valid = false;
      }
    }
    if (letter != "-" && valid == true){
      console.log("Winner!");
      incrementScore(turn);
      clearBoard();
    }

}

incrementScore = function(turn) {
  if (turn == 0){
    oScore_span.innerHTML = ++oScore;
  } else {
    xScore_span.innerHTML = ++xScore;
  }
}

clearBoard = function() {
  for (i = 0; i < 9; i++){
    spaces[i].innerHTML = "-";
  }
}
