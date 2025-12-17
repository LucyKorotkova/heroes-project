package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        while (hasAliveUnits(playerArmy) && hasAliveUnits(computerArmy)) {
            List<Unit> allAlive = new ArrayList<>();


            for (Unit unit : playerArmy.getUnits()) {
                if (unit.isAlive()) {
                    allAlive.add(unit);
                }
            }

            for (Unit unit : computerArmy.getUnits()) {
                if (unit.isAlive()) {
                    allAlive.add(unit);
                }
            }


            allAlive.sort(Comparator.comparingInt(Unit::getBaseAttack).reversed());

            for (Unit unit : allAlive) {
                if (!unit.isAlive()) continue;

                Unit target = unit.getProgram().attack();
                if (target != null) {
                    printBattleLog.printBattleLog(unit, target);
                }
                Thread.sleep(200);
            }
        }
    }

    private boolean hasAliveUnits(Army army) {
        for (Unit unit : army.getUnits()) {
            if (unit.isAlive()) return true;
        }
        return false;
    }
}