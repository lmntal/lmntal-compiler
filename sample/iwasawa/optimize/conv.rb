#!/usr/bin/ruby
# -*- coding: utf-8 -*-

flag = true
isInit = true
rulesetIndex = 0;
ruleIndex = 0;


print "rulesets{\n\n"

STDIN.each do | line |
  line.chomp!
  if line == "" || line =~ /Inline/ 
  elsif /Compiled\sRuleset\s*@([0-9]+)/ =~line # ruleset の開始
    if isInit
      print "initRuleset{ +RulesetIndex#{rulesetIndex}, ruleset(#{$1}), \n"
    else
      i = 1;
      print "ruleList = [ RuleIndex#{rulesetIndex}_0"
      while i < ruleIndex
        print ", RuleIndex#{rulesetIndex}_#{i}"
        i += 1;
      end
      print " ].\n\n"
      rulesetIndex += 1;
      ruleIndex = 0;
      print "}.\n"
      print "ruleset{ +RulesetIndex#{rulesetIndex}, ruleset(#{$1}), \n"
    end
  elsif line == "Compiled Rule " # rule の開始
    print "rule{\n+RuleIndex#{rulesetIndex}_#{ruleIndex}, compiledRule = [\n"
    ruleIndex += 1;
  elsif line =~ /atommatch/
    flag = false
  elsif isInit && line =~ /body/
    flag = true
  elsif !isInit && line =~ /memmatch/
    flag = true
  elsif flag
    op = line.sub(/\'\[\]\'_([0-9]+)/, 'functor(\'listnil\', \1)') # [] アトムは先に特別扱い
    op = op.sub(/\'(\w+)\'.\'(\w+)\'_([0-9]+)/, 'moduleFunctor(\1, \2, \3)') # io.use を特別扱い
    op = op.sub(/\'([\w\$]+)\'_([0-9]+)/, 'functor(\1, \2)') # ファンクタを lmntal syntax に
    op = op.sub(/(\$\w+)_([0-9]+)/, 'proxyFunctor("\1", \2)') # ファンクタを lmntal syntax に
    op = op.sub(/(-[0-9]+)_([0-9]+)/, 'intFunctor(\1, \2)') #　int アトムを特別扱い
    op = op.sub(/([0-9]+)_([0-9]+)/, 'intFunctor(\1, \2)') #　int アトムを特別扱い
    op = op.sub(/\"([\w\s@]+)\"_([0-9]+)/, 'stringFunctor(\1, \2)') # string アトムを特別扱い
    op = op.sub(/\'.\'+_([0-9]+)/, 'functor(\'.\', \1)')  # . アトムの場合を特別扱い
    op = op.sub(/@([0-9]+)/, 'rulesetNum(\1)') # ルールセット番号を lmntal syntax に

    op = op.sub(/([a-z]+)\s*(\[[^\]]*\])/, '[\1, \2]') # 命令と引数をリストへ

    op = op.sub(/listnil/, '[]') # エスケープした [] を戻す
    if op =~ /\s*\[\s*proceed\s*,\s*\[\s*\]\s*\]$/ # rule の終了
      isInit = false
      print "#{op}].\n}.\n"
    else
      print "#{op}, \n"
    end
  end
end


i = 1;
print "ruleList = [ RuleIndex#{rulesetIndex}_0"
while i < ruleIndex
  print ", RuleIndex#{rulesetIndex}_#{i}"
  i += 1;
end
print " ].\n\n"
rulesetIndex += 1;

print "}.\n"

i = 1;
print "rulesetList = [ RulesetIndex0"
while i < rulesetIndex
  print ", RulesetIndex#{i}"
  i += 1;
end
print " ]."


print "}.\n"
