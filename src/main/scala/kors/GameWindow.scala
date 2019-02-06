package kors

import javafx.scene.control.{Label, TitledPane}
import kors.GameBoard.{Draw, GameState, InProgress, Victory}
import kors.GameBoard.GameSymbol._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData._
import scalafx.scene.control.{Alert, Button}
import scalafx.scene.layout.{ColumnConstraints, GridPane, HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text


object GameWindow extends JFXApp {

  var myGame = GameBoard.newGame()

  def resetGame: Unit = {
    myGame = GameBoard.newGame()
    setGameStateToGrid
  }

  def closeGame: Unit = stage.close

  var titleText = new Text {
    text = "TicTacToe "
    style = "-fx-font-size: 20pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Gray, WhiteSmoke)
    )
  }

  var footerWithBtts = new HBox {
    children = Seq(
      new Button {
        text = "new game"
        onAction = { _ => resetGame }
      },
      new Button {
        text = "exit"
        onAction = { _ => closeGame }
      }
    )
  }

  def toStringSymbol: GameSymbol => String = {
    case CrossS => "╳"
    case CircleS => "◯"
  }


  def setGameStateToGrid: Unit = ???

  def showAlert(x: GameState) = {

    def myAlert(myMessage: String) = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "TicTacToe"
      contentText = myMessage + "New game?"

      //onHiding = { _ => {stage.close()}}
    }.showAndWait() match {
      case Some(x) => x.buttonData match {
        case CancelClose => closeGame
        case OKDone => resetGame
        case _ => {}
      }
      case None => {}
    }

    x match {
      case Draw => myAlert("draw.")
      case Victory(CrossS) => myAlert("╳ wins. ")
      case Victory(CircleS) => myAlert("◯ wins. ")
    }
  }

  def getMyButton(c: Int, r: Int) = new Button {
    text = " "
    onAction = { _ => {
      val result = myGame.setField(c, r)
      if (result.isDefined) {
        setGameStateToGrid
        if (result.get.state != InProgress) {
          bttnArr.flatten.foreach(_.disable = true)
          showAlert(result.get.state)
        }
      }
    }
    }
  }

  /*def getButtonsArr = {
    myGame.getGameBoard.zipWithIndex.map({
      case (s, j) => s.zipWithIndex.map({
        case (_, i) => getMyButton(j, i)
      })
    })
  }*/

  def getButtonsArr: Array[Array[Button]] = ???

  var gridPane = new GridPane()
  gridPane.setGridLinesVisible(true)

  val bttnArr: Array[Array[Button]] = getButtonsArr
  bttnArr.zipWithIndex.foreach({
    case (s, i) => s.zipWithIndex.foreach({
      case (s1, j) => gridPane.add(s1, j, i)
    })
  })

  setGameStateToGrid

  new Label()
  stage = new JFXApp.PrimaryStage {
    title.value = "TicTacToe"
    scene = new Scene {
      fill = Black
      content = new VBox {
        children = Seq(
          new HBox(titleText),
          gridPane,
          footerWithBtts
        )
      }
    }
  }

}
