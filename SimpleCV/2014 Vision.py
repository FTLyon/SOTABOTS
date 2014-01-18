import os
import glob
import copy
import time
import re
import NetworkTest
from SimpleCV import *
#cam = Camera()

i = 0
while (i < 10000):
	#img = cam.getImage()
	#blobs = img.findBlobs()
	#test = [b for b in blobs if b.isRectangle(tolerance=0.015)]
	#test = FeatureSet([b for b in blobs if b.isRectangle(tolerance=0.045) and b.width() > b.height() + 40])
	#test.draw(color=Color.RED,width=4)
	#img.drawText(str(test.coordinates()))
	#img.show()
	NetworkTest.send(1)
	#print("SENT.")
	i += .001
	time.sleep(.1)
NetworkTest.send("Stopped.")
	
	