package kata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameTest {
	Game game = new Game();

	@DisplayName("Num rolls with amount of pins knocked down result in expected score")
	@ParameterizedTest(name = "{index} ==> {0} roll(s) with {1} pins down = {2} score")
	@CsvSource({"2, 4, 8", "4, 3, 12", "3, 5, 20"})
	void xRollsResultInExpectedScore(int numRolls, int pinsKnockedDown, int expected) {
		rollMultiple(numRolls, pinsKnockedDown);
		assertThat(game.score()).isEqualTo(expected);
	}

	@DisplayName("Full game result in expected score")
	@ParameterizedTest(name = "{index} ==> {0} roll(s) with {1} pins down = {2} score")
	@CsvSource({"21, 5, 150", "20, 0, 0", "20, 1, 20", "12, 10, 300"})
	void fullGamesResultInExpectedScore(int numRolls, int pinsKnockedDown, int expected) {
		rollMultiple(numRolls, pinsKnockedDown);
		assertThat(game.score()).isEqualTo(expected);
	}

	@DisplayName("Too many rolls result in exception")
	@ParameterizedTest(name = "{index} ==> {0} roll(s) with {1} pins down throws exception")
	@CsvSource({"21, 5", "20, 1"})
	void oneRollMoreThanFullGameResultInException(int numRolls, int pinsKnockedDown) {
		rollMultiple(numRolls, pinsKnockedDown);
		assertThatThrownBy(() -> game.roll(5)).
				hasMessage("The game is over\nPlease start a new game").
				isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("Strike result in expected score")
	@Test
	void strikeResultInExpectedScore() {
		rollMultiple(1, 10);
		rollMultiple(18, 1);
		assertThat(game.score()).isEqualTo(30);
	}

	private void rollMultiple(int numRolls, int pinsKnockedDown) {
		for (int i = 0; i < numRolls; i++)
			game.roll(pinsKnockedDown);
	}
}
