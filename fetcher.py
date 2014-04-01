import urllib
import os, os.path
import sys

arg = sys.argv[1]

for root, dirs, filenames in os.walk(arg):
    for f in filenames:
        file = open(os.path.join(root, f), 'r')
        url = file.read()
        cleanName = os.path.splitext(f)[0]
        print os.path.join(root, cleanName + ".png")
        urllib.urlretrieve(url, os.path.join(root, cleanName + ".png"))

