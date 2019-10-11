package org.cosma.ui.skin;


import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.skin.ProgressIndicatorSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LinearProgressBarSkin extends ProgressIndicatorSkin {

    private StackPane track;
    private StackPane bar;
    private Animation indeterminateTransition;
    private Region clip;

    private Timeline refreshTimeline;

    public LinearProgressBarSkin(ProgressBar bar) {
        super(bar);

        registerChangeListener(bar.visibleProperty(), obs -> updateAnimation());

        initialize();
        bar.setVisible(false);
    }

    private void initialize() {
        track = new StackPane();
        track.getStyleClass().setAll("track");

        bar = new StackPane();
        bar.getStyleClass().setAll("bar");

        refreshTimeline = new Timeline();
        refreshTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(200)));
        refreshTimeline.setOnFinished(e -> indeterminateTransition.play());

        clip = new Region();
        clip.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        getChildren().setAll(track, bar);

    }

    @Override
    public double computeBaselineOffset(double topInset, double rightInset, double bottomInset, double leftInset) {
        return Node.BASELINE_OFFSET_SAME_AS_HEIGHT;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Math.max(100, leftInset + bar.prefWidth(getSkinnable().getWidth()) + rightInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return topInset + bar.prefHeight(width) + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefWidth(height);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefHeight(width);
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        track.resizeRelocate(x, y, w, h);
        double barWidth = 0;
        bar.resizeRelocate(x, y, getSkinnable().isIndeterminate() ? w : barWidth, h);
        clip.resizeRelocate(0, 0, w, h);

        if (getSkinnable().isIndeterminate()) {
            createIndeterminateTimeline();

            refreshTimeline.stop();
            refreshTimeline.play();

            bar.setClip(clip);
        }
    }


    private void pauseTimeline(boolean pause) {
        if (indeterminateTransition == null) {
            createIndeterminateTimeline();
        }
        if (pause) {
            indeterminateTransition.pause();
        } else {
            indeterminateTransition.playFromStart();
        }
    }

    private void updateAnimation() {
        ProgressIndicator control = getSkinnable();
        final boolean isVisible = control.isVisible();

        if (indeterminateTransition != null) {
            pauseTimeline(!isVisible);
        } else if (isVisible) {
            createIndeterminateTimeline();
        }
    }


    private void createIndeterminateTimeline() {
        if (indeterminateTransition != null)
            clearAnimation();


        double dur = 1.7;
        ProgressIndicator control = getSkinnable();
        final double w = control.getWidth() - (snappedLeftInset() + snappedRightInset());
        indeterminateTransition = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(clip.scaleXProperty(), 0.0, Interpolator.EASE_IN),
                        new KeyValue(clip.translateXProperty(), -w / 2, Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(0.5 * dur),
                        new KeyValue(clip.scaleXProperty(), 0.4, Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(0.9 * dur),
                        new KeyValue(clip.translateXProperty(), w / 2, Interpolator.LINEAR)
                ),
                new KeyFrame(
                        Duration.seconds(1 * dur),
                        new KeyValue(clip.scaleXProperty(), 0.0, Interpolator.EASE_OUT)
                ));
        indeterminateTransition.setCycleCount(Timeline.INDEFINITE);
    }

    private void clearAnimation() {
        indeterminateTransition.stop();
        ((Timeline) indeterminateTransition).getKeyFrames().clear();
        indeterminateTransition = null;
    }

    @Override
    public void dispose() {
        super.dispose();

        if (indeterminateTransition != null)
            clearAnimation();
    }

}
