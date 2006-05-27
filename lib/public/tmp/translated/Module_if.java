package translated;
import translated.module_if.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_if{
	private static Ruleset[] rulesets = {Ruleset603.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		loadSystemRulesetFromModule("boolean");
		runtime.Inline.inlineSet.put("module_boolean.lmn", new runtime.InlineUnit("boolean.lmn"));
		runtime.Inline.inlineSet.put("public/if.lmn", new runtime.InlineUnit("public/if.lmn"));
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
