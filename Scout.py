from xlwt import *
from xlutils.copy import copy
from xlrd import *
from time import sleep

inputs = ['Match','Auto Hot','Auto High','Auto Low','Auto Zone','Tele Pass','Tele Score','Tele Truss']

def initExcel(name):
	file = name + '.xls'
	try:
		w = copy(open_workbook(file))
	except:
		w = Workbook()
		ws = w.add_sheet(name)
		for i in range(1,50):
			ws.write(i,0,str(i))
		for n in range(0,len(inputs)):
			ws.write(0,n,inputs[n])
		w.save(file)
	print("Initialized...")

def write(name,match,data):
	file = name + '.xls'
	try:
		w = copy(open_workbook(file))
	except:
		initExcel(name)
		w = copy(open_workbook(file))
	row = match
	col = 1
	for d in data:
		w.get_sheet(0).write(row,col,str(d))
		col += 1
	w.save(file)
	print("Data written to " + file)

#initExcel("2557")
#write(1,(1,3,5,13,26))
print("Yep")
sleep(2)
