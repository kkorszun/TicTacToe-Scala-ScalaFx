package kors

import kors.GameBoard._


case class GameBoard(width: Int, height: Int,
                     setFields: Set[BoardField], playerRound: GameSymbol.GameSymbol, state: GameState) {

  private def checkLines(gameSymbol: GameSymbol.GameSymbol, fields: Set[BoardField]): Boolean =
    fields
      .toVector
      .filter(_.symbol == gameSymbol)
      .groupBy(_.x)
      .values
      .exists(_.size == width)


  private def checkColumns(gameSymbol: GameSymbol.GameSymbol, fields: Set[BoardField]): Boolean =
    fields
      .toVector
      .filter(_.symbol == gameSymbol)
      .groupBy(_.y)
      .values
      .exists(_.size == width)

  private def checkLeftDiagonal(gameSymbol: GameSymbol.GameSymbol, fields: Set[BoardField]): Boolean =
    fields
    .toVector
    .filter(_.symbol == gameSymbol)
    .count(elem => elem.x == elem.y)
    .equals(width)

  private def checkRightDiagonal(gameSymbol: GameSymbol.GameSymbol, fields: Set[BoardField]): Boolean =
    fields
      .toVector
      .filter(_.symbol == gameSymbol)
      .count(elem => (width - elem.x) == elem.y)
      .equals(width)

  private def checkSymbol(gameSymbol: GameSymbol.GameSymbol, fields: Set[BoardField]): Boolean =
    checkLines(gameSymbol, fields) || checkColumns(gameSymbol, fields) ||
      checkLeftDiagonal(gameSymbol, fields) || checkRightDiagonal(gameSymbol, fields)

  private def checkResult(fields: Set[BoardField]): GameState = {
    val resultList = GameSymbol.values.foldLeft(Set[GameState]()) { (acc, elem) =>
      if (checkSymbol(elem, fields)) acc + Victory(elem)
      else acc
    }

    if (resultList.nonEmpty) resultList.head
    else {
      if (setFields.size == width * height) InProgress
      else Draw
    }
  }

  def setField(x: Int, y: Int): Option[GameBoard] = {

    if(state == InProgress) {
      val fieldExist = setFields.exists {
        case BoardField(x1, y1, _) if (x1, y1) == (x, y) => true
        case _ => false
      }

      if (fieldExist) None
      else {
        val newFields = setFields + BoardField(x, y, playerRound)
        val newState = checkResult(newFields)
        Option(GameBoard(width, height,  newFields, GameSymbol.nextSymbol(playerRound), newState))
      }

    } else None
  }
}


object GameBoard {

  case class BoardField(x: Int, y: Int, symbol: GameSymbol.GameSymbol)

  object GameSymbol extends Enumeration {
    type GameSymbol = Value
    val CircleS, CrossS = Value

    def nextSymbol: GameSymbol => GameSymbol = {
      case CircleS => CrossS
      case CrossS => CircleS
    }
  }

  sealed trait GameState
  object InProgress extends GameState
  object Draw extends GameState
  case class Victory(symbol: GameSymbol.GameSymbol) extends GameState

  def newGame() = GameBoard(3,3,Set(),GameSymbol.CrossS, InProgress)

}
