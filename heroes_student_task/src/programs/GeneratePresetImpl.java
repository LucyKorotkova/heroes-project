package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        List<Unit> sortedByEfficiency = new ArrayList<>(unitList);

        sortedByEfficiency.sort((u1, u2) -> {
            double eff1 = (double) u1.getBaseAttack() / u1.getCost();
            double eff2 = (double) u2.getBaseAttack() / u2.getCost();
            return Double.compare(eff2, eff1);
        });

        List<Unit> selectedUnits = new ArrayList<>();
        int totalCost = 0;
        Set<String> addedTypes = new HashSet<>();

            for (Unit template : sortedByEfficiency) {
            if (totalCost + template.getCost() > maxPoints) continue;
            if (!addedTypes.contains(template.getUnitType())) {
                Map<String, Double> safeAttackBonuses = new HashMap<>(
                        Optional.ofNullable(template.getAttackBonuses()).orElse(Collections.emptyMap()));
                Map<String, Double> safeDefenceBonuses = new HashMap<>(
                        Optional.ofNullable(template.getDefenceBonuses()).orElse(Collections.emptyMap()));
                String uniqueName = template.getUnitType() + "_0_" + UUID.randomUUID();
                Unit copy = new Unit(
                        uniqueName,
                        template.getUnitType(),
                        template.getHealth(),
                        template.getBaseAttack(),
                        template.getCost(),
                        template.getAttackType(),
                        safeAttackBonuses,
                        safeDefenceBonuses,
                        0, 0
                );
                selectedUnits.add(copy);
                totalCost += template.getCost();
                addedTypes.add(template.getUnitType());
            }
        }

        for (Unit template : sortedByEfficiency) {
            for (int i = 1; i < 11; i++) {
                if (totalCost + template.getCost() > maxPoints) break;
                Map<String, Double> safeAttackBonuses = new HashMap<>(
                        Optional.ofNullable(template.getAttackBonuses()).orElse(Collections.emptyMap()));
                Map<String, Double> safeDefenceBonuses = new HashMap<>(
                        Optional.ofNullable(template.getDefenceBonuses()).orElse(Collections.emptyMap()));
                String uniqueName = template.getUnitType() + "_" + i + "_" + UUID.randomUUID();
                Unit copy = new Unit(
                        uniqueName,
                        template.getUnitType(),
                        template.getHealth(),
                        template.getBaseAttack(),
                        template.getCost(),
                        template.getAttackType(),
                        safeAttackBonuses,
                        safeDefenceBonuses,
                        0, 0
                );
                selectedUnits.add(copy);
                totalCost += template.getCost();
            }
        }

        if (selectedUnits.isEmpty() && !unitList.isEmpty()) {
            Unit template = unitList.get(0);
            Map<String, Double> safeAttackBonuses = new HashMap<>(
                    Optional.ofNullable(template.getAttackBonuses()).orElse(Collections.emptyMap()));
            Map<String, Double> safeDefenceBonuses = new HashMap<>(
                    Optional.ofNullable(template.getDefenceBonuses()).orElse(Collections.emptyMap()));
            String uniqueName = template.getUnitType() + "_fallback_" + UUID.randomUUID();
            Unit copy = new Unit(
                    uniqueName,
                    template.getUnitType(),
                    template.getHealth(),
                    template.getBaseAttack(),
                    template.getCost(),
                    template.getAttackType(),
                    safeAttackBonuses,
                    safeDefenceBonuses,
                    0, 0
            );
            selectedUnits.add(copy);
        }

        return new Army(selectedUnits);
    }
}