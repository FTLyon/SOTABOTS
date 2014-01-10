import os
import glob
import copy
import time
import re
from SimpleCV import *
from socket import *
cam = Camera()
myHost = ''
myPort = 2000
s = socket(AF_INET, SOCK_STREAM)
s.bind((myHost, myPort))
s.listen(5)
i = 0
while True:
	img = cam.getImage()
	blobs = img.findBlobs()
	#test = [b for b in blobs if b.isRectangle(tolerance=0.015)]
	test = FeatureSet([b for b in blobs if b.isRectangle(tolerance=0.045) and b.width() > b.height() + 40])
	test.draw(color=Color.RED,width=4)
	img.drawText(str(test.coordinates()))
	img.show()
	if i < 10:
		pass
	else:
		connection, address = s.accept()
		connection.send(test)
		i = 0
	