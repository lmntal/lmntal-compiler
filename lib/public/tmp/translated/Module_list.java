package translated;
import translated.module_list.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_list{
	private static Ruleset[] rulesets = {Ruleset613.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		loadSystemRulesetFromModule("queue");
		runtime.Inline.inlineSet.put("module_queue.lmn", new runtime.InlineUnit("queue.lmn"));
		loadSystemRulesetFromModule("queue");
		runtime.Inline.inlineSet.put("module_queue.lmn", new runtime.InlineUnit("queue.lmn"));
		loadSystemRulesetFromModule("queue");
		runtime.Inline.inlineSet.put("module_queue.lmn", new runtime.InlineUnit("queue.lmn"));
		runtime.Inline.inlineSet.put("public/list.lmn", new runtime.InlineUnit("public/list.lmn"));
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
