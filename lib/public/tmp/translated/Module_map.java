package translated;
import translated.module_map.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_map{
	private static Ruleset[] rulesets = {Ruleset619.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		loadSystemRulesetFromModule("queue");
		loadSystemRulesetFromModule("queue");
		loadSystemRulesetFromModule("queue");
	}
	private static void loadSystemRulesetFromModule(String moduleName) {
		try {
			Class c = Class.forName("translated.Module_" + moduleName);
			java.lang.reflect.Method method = c.getMethod("loadUserDefinedSystemRuleset", null);
			method.invoke(null, null);
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (java.lang.reflect.InvocationTargetException e) {
		}
	}
}
