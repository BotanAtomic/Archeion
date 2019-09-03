package org.archeion.ui.components;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.archeion.ui.utils.UI;

import java.util.ArrayList;
import java.util.List;

import static org.archeion.ui.utils.Animation.fadeTransition;
import static org.archeion.ui.utils.UI.SEARCH_TRANSITION_TIME;

public class SearchBar {

    private static Timeline timeline;

    private static TextField search;
    private static StackPane resetSearchIcon;

    private static List<Transition> currentTransitions;

    public static void initialize(TextField search, StackPane resetSearchIcon) {
        SearchBar.search = search;
        SearchBar.resetSearchIcon = resetSearchIcon;
        SearchBar.timeline = new Timeline();
        SearchBar.currentTransitions = new ArrayList<>();

        search.setOpacity(0);
        resetSearchIcon.setOpacity(0);

        search.textProperty().addListener((observable, oldValue, newValue) -> startTimer());

        resetSearchIcon.setOnMouseClicked(e -> reset(e));
    }

    private static void addTransition(Transition transition) {
        if (transition != null) {
            currentTransitions.add(transition);
            transition.setOnFinished(e -> currentTransitions.remove(transition));
        }
    }

    private static void check() {
        if (!UI.isVisible(search))
            return;

        String newValue = search.getText();

        addTransition(fadeTransition(newValue.isBlank(), SEARCH_TRANSITION_TIME, resetSearchIcon));

        //TODO: callback to search
    }

    private static void startTimer() {
        if (timeline != null)
            timeline.stop();

        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> check()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void hide() {
        if (UI.isVisible(search))
            toggle();
    }

    public static void toggle() {
        timeline.stop();
        currentTransitions.forEach(Animation::stop);

        search.clear();

        if (UI.isVisible(search))
            addTransition(fadeTransition(true, SEARCH_TRANSITION_TIME, resetSearchIcon));


        addTransition(fadeTransition(UI.isVisible(search), SEARCH_TRANSITION_TIME, search));
    }

    private static void reset(MouseEvent event) {
        resetSearchIcon.setOpacity(0);
        search.clear();
        event.consume();
    }
}
