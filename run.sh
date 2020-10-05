#!/bin/bash
#Project finder and compiler
#Pretty alpha version, so it's safer to compile from commandline

match="public static void main\("             # regex match
files=`egrep -lr --include=*.java "$match" .` # Get files
declare -A javaruns                           # Hashmap for files and numbers 

clear
echo "============ COMPILE AND RUN PROJECTS ============"
it=0
echo "[0] Exit"
for file in $files
  do
    it=$((it+1))
    echo "[$it] $file"
    javaruns[$it]=$file
  done

echo "============ COMPILE AND RUN PROJECTS ============"

echo -n "Select a [number] to run: "
read selected

until [ $selected -ge "0" ] && [ $selected -le ${#javaruns[@]} ]
do
  clear
  echo "============ COMPILE AND RUN PROJECTS ============"
  it=0
  echo "[0] Exit"
  for file in $files
    do
      it=$((it+1))
      echo "[$it] $file"
      javaruns[$it]=$file
    done

  echo "============ COMPILE AND RUN PROJECTS ============"
  echo -n "[WRONG NUMBER]  Select a [number] to run: "
  read selected
done

# Compile and run
if [ $selected -ne '0' ]
then
      clear
      echo "Compiling ${javaruns[$selected]}..."
      cd `dirname "${javaruns[$selected]}"` && javac `basename "${javaruns[$selected]}"` && echo "======== EXECUTE ========" && java "Main"
else
  clear
fi