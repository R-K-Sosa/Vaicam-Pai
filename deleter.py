import os, glob, time 

While True: 
	oldest = max(glob.iglob('*.jpg'), key=os.path.getctime) 
	os.remove(oldest)
	time.sleep(3)