package kors

import org.scalatest.FlatSpec

import kors.GameEngine.GameSymbol._
import kors.GameEngine.GameState._

class GameEngineTest extends FlatSpec {
  behavior of "GameEngineTes"

  it should "return board with only Empty symbols no move was taken " in {
    val game  = new GameEngine
    assert(game.getGameBoard.flatten.forall(_ == EmptyS))
  }

  it should "return board with sign in given position after one move" in {
    val game = new GameEngine
    game.nextMove(0,0)
    assertResult(CrossS)(game.getGameBoard(0)(0))

    game.nextMove(2,1)
    assertResult(CircleS)(game.getGameBoard(2)(1))

    game.nextMove(2,2)
    assertResult(CrossS)(game.getGameBoard(2)(2))
  }


  it should "not change game board after doubling move" in{
    val game = new GameEngine

    game.nextMove(2,1)
    val res1 = game.getGameBoard

    game.nextMove(2,1)
    val res2 =  game.getGameBoard

    assertResult(res1)(res2)
  }

  it should "signalize if player win" in {
    val game = new GameEngine

    game.nextMove(0,0) //cross
    game.nextMove(1,1) //circle
    game.nextMove(0,1) // cross
    game.nextMove(1,2)//circle
    val result = game.nextMove(0,2) //cross

    assertResult((CrossV,true))(result)
  }
}
