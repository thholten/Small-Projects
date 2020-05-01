package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GridWindow extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vboxMain = new VBox(5);
		vboxMain.setPadding(new Insets(5));

		Scene scene = new Scene(vboxMain, 520, 575);

		BorderPane borderHeader = new BorderPane();
		borderHeader.setPadding(new Insets(10, 0, 0, 2));

		Text textTitle = new Text("Pixel Grid");
		textTitle.setFont(Font.font("Helvetica", 24));
		borderHeader.setLeft(textTitle);

		Text textAuthor = new Text("By Thomas von Holten");
		textAuthor.setFont(Font.font("Times New Roman", 12));
		borderHeader.setRight(textAuthor);
		BorderPane.setAlignment(textAuthor, Pos.BOTTOM_RIGHT);

		vboxMain.getChildren().add(borderHeader);
		vboxMain.getChildren().add(new Separator());

		// editor should fit 20 pixels
		TilePane tileEditor = new TilePane();
		tileEditor.setPadding(new Insets(10, 25, 10, 25));
//		tileEditor.setHgap(2.5);
//		tileEditor.setVgap(2.5);
		tileEditor.setAlignment(Pos.CENTER);

		for (int i = 0; i < Math.pow((500 / 20) - (2 * 2.5), 2); i++) {

			Rectangle pixel = new Rectangle(20, 20);
			pixel.setStroke(Color.WHITE);
			pixel.setStrokeWidth(1.25);

			pixel.setOnDragDetected(e -> {
				pixel.startFullDrag();
			});

			pixel.setOnMousePressed(e -> {
				if (pixel.getFill().equals(Paint.valueOf("black"))) {
					pixel.setFill(Paint.valueOf("lightGray"));
				} else
					pixel.setFill(Paint.valueOf("black"));

			});

			pixel.setOnMouseDragEntered(e -> {

				Object source = e.getGestureSource();
				if (source instanceof Rectangle) {
					if (((Rectangle) source).getFill().equals(Color.BLACK)) {
						pixel.setFill(Color.BLACK);
					} else
						pixel.setFill(Color.LIGHTGREY);
				}
			});

			tileEditor.getChildren().add(pixel);
		}

		vboxMain.getChildren().add(tileEditor);

		HBox hboxControls = new HBox(5);
		hboxControls.setAlignment(Pos.CENTER);
		
		ClearColor clearColor = ClearColor.BLACK;

		Button buttonClear = new Button("Clear");
		buttonClear.setOnAction(e -> {
			for (Node pixel : tileEditor.getChildren()) {
				if (pixel instanceof Rectangle) {
					((Rectangle) pixel).setFill(clearColor.getColor());
				}
			}
		});
		
		buttonClear.setAlignment(Pos.CENTER_LEFT);
		hboxControls.getChildren().add(buttonClear);

		
		Button buttonInvert = new Button("Invert");
		buttonInvert.setOnAction(e -> {
			
			clearColor.switchColor();
			
			for (Node pixel : tileEditor.getChildren()) {
				if (pixel instanceof Rectangle) {
					if (((Rectangle) pixel).getFill().equals(Color.BLACK)) {
						((Rectangle) pixel).setFill(Color.LIGHTGRAY);
					}
					else
						((Rectangle) pixel).setFill(Color.BLACK);
				}
			}
		});

		buttonInvert.setAlignment(Pos.CENTER_RIGHT);
		hboxControls.getChildren().add(buttonInvert);

		vboxMain.getChildren().add(hboxControls);

		primaryStage.setTitle("Pixel Grid");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
	}

	enum ClearColor {

		BLACK (Color.BLACK), 
		LIGHTGRAY (Color.LIGHTGRAY);
		
		private Color color;
		
		ClearColor(Color color){
			this.color = color;
		}
		
		Color getColor() {
			return color;
		}
		
		void switchColor() {
			if (color == Color.BLACK) {
				color = Color.LIGHTGRAY;
			} else color = Color.BLACK;
		}

	}
	

}
