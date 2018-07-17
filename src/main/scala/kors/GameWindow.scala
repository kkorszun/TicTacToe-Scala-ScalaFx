package kors

import javafx.scene.control.{Label, TitledPane}
import kors.GameEngine.GameSymbol.{CircleS, CrossS, EmptyS, GameSymbol}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{ColumnConstraints, GridPane, HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text


object GameWindow extends JFXApp {

  val myGame = new GameEngine

  var titleText = new Text {
    text = "TicTacToe "
    style = "-fx-font-size: 20pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Gray, WhiteSmoke)
    )
  }

  def toStringSymbol: GameSymbol => String = {
    case EmptyS   =>  "  "
    case CrossS   =>  "╳"
    case CircleS  =>  "◯"
  }

  def setGameStateToGrid :Unit = {
    myGame.getGameBoard.flatten.zip(bttnArr).foreach(_ match{
      case (x,y) => {
        y.text.value_=  { this toStringSymbol x }
      }
    })

  }



  def getMyButton(c :Int,  r:Int) = new Button{
    text = " "
    //◯
    onAction = { value => {
      myGame.nextMove(c,r)
      setGameStateToGrid
    }
    }
  }

  var btt11 = getMyButton(0,0)
  var btt12 = getMyButton(0,1)
  var btt13 = getMyButton(0,2)
  var btt21 = getMyButton(1,0)
  var btt22 = getMyButton(1,1)
  var btt23 = getMyButton(1,2)
  var btt31 = getMyButton(2,0)
  var btt32 = getMyButton(2,1)
  var btt33 = getMyButton(2,2)

  val bttnArr = Array(
    btt11,btt12, btt13,
    btt21, btt22, btt23,
    btt31, btt32, btt33)

  var gridPane = new GridPane()
  gridPane.setGridLinesVisible(true)
  gridPane.add(btt11,0,0)
  gridPane.add(btt12,1,0)
  gridPane.add(btt13,2,0)
  gridPane.add(btt21,0,1)
  gridPane.add(btt22,1,1)
  gridPane.add(btt23,2,1)
  gridPane.add(btt31,0,2)
  gridPane.add(btt32,1,2)
  gridPane.add(btt33,2,2)

  myGame.nextMove(1,1)
  myGame.nextMove(1,0)
  myGame.nextMove(0,2)
  myGame.nextMove(0,0)
  setGameStateToGrid

  new Label()
  stage = new JFXApp.PrimaryStage {
    title.value = "TicTacToe"
    scene = new Scene {
      fill = Black
      content = new VBox{
        children = Seq(
          new HBox(titleText),
          gridPane
        )
      }
    }
  }

}
