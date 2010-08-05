#!/usr/bin/ruby
# -*- coding: utf-8 -*-

flag = true
isInit = true

print "rulesets{\n\n"

STDIN.each do | line |
  line.chomp!
  if line == "" || line =~ /Inline/ 
    #print "empty"
  elsif /Compiled\sRuleset\s*@([0-9]+)/ =~line
    if isInit
      print "initRuleset{ ruleset(#{$1}), \n"
#      print "init_compiledRuleset(#{$1})=[\n"
    else 
      print "}.\n"
      print "ruleset{ ruleset(#{$1}), \n"
#      print "compiledRuleset(#{$1})=["
    end
  elsif line == "Compiled Rule "
    print "compiledRule = [\n"
  elsif line =~ /atommatch/
    flag = false
  elsif isInit && line =~ /body/
    flag = true
  elsif !isInit && line =~ /memmatch/
    flag = true
  elsif flag
    #op = line.sub(/[a-z]+/, '\&,') # 命令と引数の間にカンマ
    
    #op = op.sub(/\'([0-9a-z]+)\'+_([0-9]+)/, 'functor(\1, \2)') # ファンクタを lmntal syntax に
    op = line.sub(/\'\[\]\'_([0-9]+)/, 'functor(\'listnil\', \1)') # [] アトムは先に特別扱い
    op = op.sub(/\'(\w+)\'+_([0-9]+)/, 'functor(\1, \2)') # ファンクタを lmntal syntax に
    op = op.sub(/([0-9]+)+_([0-9]+)/, 'intFunctor(\1, \2)') #　int アトムを特別扱い
    op = op.sub(/\'.\'+_([0-9]+)/, 'functor(\'.\', \1)')  # . アトムの場合を特別扱い
    op = op.sub(/@([0-9]+)/, 'rulesetNum(\1)') # ルールセット番号を lmntal syntax に

    op = op.sub(/([a-z]+)\s*(\[[^\]]*\])/, '[\1, \2]') # 命令と引数をリストへ

    op = op.sub(/listnil/, '[]') # エスケープした [] を戻す
    if op =~ /proceed/
      isInit = false
      print "#{op}].\n"
    else
      print "#{op}, \n"
    end
  end
end

print "}.\n"

print "}.\n"
