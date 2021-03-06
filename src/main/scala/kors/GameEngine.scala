package kors

class GameEngine {

  import GameEngine._
  import GameState._
  import GameSymbol._

  private val gameBoard = Array.fill[GameSymbol](3,3)(EmptyS)
  private var moveCounter = 0
  private var globalState = NonV
  private var roundSymbol = CrossS

  private def setSymbol(gs: GameSymbol, x:Int , y:Int) = {
    if(x<0 || x>= 3 || y<0 || y>=3) false
    else gameBoard(x)(y) match {
       case EmptyS => gameBoard(x)(y) = gs; true
       case _ => false
     }
    }

  private def checkResult:GameState = {
    import GameState._
    val flattenBoard = gameBoard.flatten
    def countSymbol(gameSymbol: GameSymbol) = flattenBoard.count(_ == gameSymbol)
    def checkLine(myGameBoard: List[List[GameSymbol]]) = myGameBoard.map {
      case List(x, y, z) if x == y && x == z => x
      case _ => EmptyS
    } match {
      case List(EmptyS,EmptyS,EmptyS) => EmptyS
      case List(x,_,_) if x != EmptyS => x
      case List(_,y,_) if y != EmptyS => y
      case List(_,_,z) if z != EmptyS => z
    }

    def boardRotate(mgb : List[List[GameSymbol]]) = List(mgb.map(_(0)),mgb.map(_(1)),mgb.map(_(2)))

    def checkCols(mgb : List[List[GameSymbol]]) = checkLine(boardRotate(mgb))

    def checkDiagonals (mgb: List[List[GameSymbol]]) = checkLine(List(
      List(mgb.head.head,mgb(1)(1), mgb(2)(2)),
      List(mgb(2).head,mgb(1)(1), mgb.head(2)),
      List(EmptyS,EmptyS,EmptyS)
    ))

    def checkWinner :GameSymbol => GameState = {
      case CrossS => CrossV
      case CircleS => CircleV
      case _ => Draw
    }

    if(countSymbol(CrossS) <3 && countSymbol(CircleS)<3) return NonV
    val gameBoardList = gameBoard.map(_.toList).toList
    val cl = checkLine(gameBoardList)
    if (cl != EmptyS) return checkWinner(cl)
    val ccc = checkCols(gameBoardList)
    if (ccc != EmptyS) return checkWinner(ccc)
    val cdg = checkDiagonals(gameBoardList)
    if (cdg != EmptyS) return checkWinner(cdg)
    if(!gameBoard.exists(_.exists(_ == EmptyS))) return Draw
    NonV
  }

  import  GameState._
  def nextMove(x :Int, y:Int) :(GameState,Boolean) = {
    if(globalState == NonV) if (setSymbol(roundSymbol, x, y)) {
      globalState = checkResult
      moveCounter += 1
      roundSymbol = roundSymbol match {
        case CrossS => CircleS
        case CircleS => CrossS
      }
      return (globalState, true)
    } else {
      return (globalState, false)
    }
    (globalState,false)
  }
  def getGameBoard = gameBoard
}

object GameEngine{
 /* object GameState extends Enumeration {
    type GameState = Value
    val CrossV, CircleV, NonV, Draw = Value
  }
  */

  /*object GameSymbol extends Enumeration {
    type GameSymbol = Value
    val EmptyS, CircleS, CrossS = Value
  }*/

}
