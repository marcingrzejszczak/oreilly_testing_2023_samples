package com.example.week3.part1.done;

import com.example.week3.part1.done.applier.DiscountApplier;
import com.example.week3.part1.done.repository.RateRepository;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.example.week3.part1")
public class ArchitectureTests {

	@ArchTest
	static final ArchTests modelTests = ArchTests.in(ModelTests.class);

	@ArchTest
	static final ArchTests repositoryTests = ArchTests.in(RepositoryTests.class);

	@ArchTest
	static final ArchTests applierTests = ArchTests.in(ApplierTests.class);

	@ArchTest
	static final ArchTests layerTests = ArchTests.in(LayerTests.class);

	@ArchTest
	static final ArchTests cycleTests = ArchTests.in(CycleTests.class);


	static class ModelTests {

		@ArchTest
		public static final ArchRule modelClassesShouldBePublic = classes().that().resideInAPackage("..model..").should().bePublic();

		@ArchTest
		public static final ArchRule modelClassesShouldNotDependOnOtherPackages = noClasses().that().resideInAPackage("..model..").should().dependOnClassesThat().resideOutsideOfPackages("..model..", "..java..");
	}

	static class RepositoryTests {
		@ArchTest
		public static final ArchRule repositoryCannotDependOnApplier = noClasses().that().resideInAPackage("..repository..")
				.should().dependOnClassesThat().resideInAPackage("..applier..");

		@ArchTest
		public static final ArchRule repositoryShouldHaveAProperSuffixAndResideInProperPackage = classes().that().implement(RateRepository.class).should().haveNameMatching(".*RateRepository").andShould().resideInAPackage("..repository..");


		@ArchTest
		public static final ArchRule repositoryShouldHavePublicInterfaces = classes().that().resideInAPackage("..repository..").and().areInterfaces().should().bePublic();

		@ArchTest
		public static final ArchRule repositoryShouldHavePackagePrivateClasses = classes().that().resideInAPackage("..repository..").and().areNotInterfaces().should().bePackagePrivate();
	}

	static class ApplierTests {

		@ArchTest
		public static final ArchRule applierCanDependOnModelAndRepository = classes().that().resideInAPackage("..applier..")
				.should().onlyDependOnClassesThat().resideInAnyPackage("..model..", "..repository..", "..applier..", "..java..", "..org.slf4j..");

		@ArchTest
		public static final ArchRule appliersShouldHaveAProperSuffixAndResideInProperPackage = classes().that().implement(DiscountApplier.class).should().haveNameMatching(".*DiscountApplier").andShould().resideInAPackage("..applier..");

		@ArchTest
		public static final ArchRule appliersShouldHavePublicInterfaces = classes().that().resideInAPackage("..applier..").and().areInterfaces().should().bePublic();

		@ArchTest
		public static final ArchRule appliersShouldHavePackagePrivateClasses = classes().that().resideInAPackage("..applier..").and().areNotInterfaces().should().bePackagePrivate();
	}

	static class LayerTests {

		@ArchTest
		public static final ArchRule layerTests = layeredArchitecture()
				.consideringOnlyDependenciesInLayers()
				.layer("Applier").definedBy("..applier..")
				.layer("Model").definedBy("..model..")
				.layer("Repository").definedBy("..repository..")

				.whereLayer("Applier").mayNotBeAccessedByAnyLayer()
				.whereLayer("Repository").mayOnlyBeAccessedByLayers("Applier")
				.whereLayer("Model").mayNotAccessAnyLayer();
	}

	static class CycleTests {

		@ArchTest
		public static final ArchRule cycleTests = slices().matching("com.example.week3.part1.(*)..").should().beFreeOfCycles();
	}

}
