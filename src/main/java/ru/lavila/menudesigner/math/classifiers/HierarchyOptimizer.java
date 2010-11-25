package ru.lavila.menudesigner.math.classifiers;

import ru.lavila.menudesigner.math.HierarchyCalculator;
import ru.lavila.menudesigner.models.*;

import java.util.List;

public class HierarchyOptimizer {
    private final List<Hierarchy> taxonomies;
    private final Hierarchy hierarchy;
    private final HierarchyCalculator calculator;
    private final CategoryEvaluator evaluator;

    public HierarchyOptimizer(ItemsList itemsList, Hierarchy hierarchy, HierarchyCalculator calculator) {
        this.hierarchy = hierarchy;
        this.taxonomies = itemsList.getTaxonomies();
        this.calculator = calculator;
        this.evaluator = new TopDownTreeEvaluator(hierarchy, calculator);
    }

    public void optimizeSubTree(Category category) {
        CategoryManipulator manipulator = new CategoryManipulator(hierarchy, category, evaluator);
        LocalSearchCategoryOptimizer optimizer = new LocalSearchCategoryOptimizer(manipulator, calculator.getMenuModel());
        manipulator.cleanup();
        hierarchy.freeze();
        Split bestSplit = manipulator.groupSplit();
        for (Hierarchy taxonomy : taxonomies) {
            Split testSplit = optimizer.optimize(taxonomy);
            if (testSplit.evaluation < bestSplit.evaluation) bestSplit = testSplit;
        }
        manipulator.cleanup();
        hierarchy.unfreeze();
        manipulator.apply(bestSplit);
        for (Element element : category.getElements()) {
            if (element instanceof Category) {
                optimizeSubTree((Category) element);
            }
        }
    }

    public void optimizeByTaxonomy(Category category, Hierarchy taxonomy) {
        CategoryManipulator manipulator = new CategoryManipulator(hierarchy, category, evaluator);
        manipulator.apply(new LocalSearchCategoryOptimizer(manipulator, calculator.getMenuModel()).optimize(taxonomy));
    }
}
