package translated;
import translated.module_sys.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_sys{
	private static Ruleset[] rulesets = {Ruleset619.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		runtime.Inline.inlineSet.put("public/sys.lmn", new runtime.InlineUnit("public/sys.lmn"));
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
