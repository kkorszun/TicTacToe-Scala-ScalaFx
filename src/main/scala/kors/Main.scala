package kors

import kors.GameEngine.GameSymbol._


object Main extends App {

  def symbolToChar: GameSymbol => Char = {
    case EmptyS => ' '
    case CrossS => 'x'
    case CircleS => 'o'
  }

  def printBoard(boardx: Array[Array[GameSymbol]]) = {
    boardx.foreach(f => {
      println(f.map(symbolToChar(_)).mkString("|"))
      println("_._._")
    })
  }


  override def main(args: Array[String]): Unit = {
    println("Hello")
    val myGame = new GameEngine
    printBoard(myGame.getGameBoard)
    println("-------------------------------")
    myGame.nextMove(1,1)
    myGame.nextMove(1,0)
    myGame.nextMove(0,2)
    myGame.nextMove(0,0)
    println(myGame.nextMove(2,0))
    printBoard(myGame.getGameBoard)
  }
}
