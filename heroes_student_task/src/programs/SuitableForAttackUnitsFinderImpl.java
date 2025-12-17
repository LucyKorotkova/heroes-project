package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> result = new ArrayList<>();

        for (List<Unit> row : unitsByRow) {
            if (!row.isEmpty()) {
                Unit candidate;

                if (isLeftArmyTarget) {
                    candidate = Collections.min(row, Comparator.comparingInt(unit -> unit.getxCoordinate()));
                } else {
                    candidate = Collections.max(row, Comparator.comparingInt(unit -> unit.getxCoordinate()));
                }

                result.add(candidate);
            }
        }

        return result;
    }
}
