import matplotlib.pyplot as plt 
import numpy as np, pandas as pd
from statsmodels.tsa.seasonal import seasonal_decompose
from statsmodels.tsa.holtwinters import ExponentialSmoothing


df = pd.read_csv("./grafana_nLogin.csv", sep=",", header=0, parse_dates=[0], index_col=[0])
tsr = df.resample(rule="0.25T").mean()

print(tsr.info())
print(tsr.describe())
print(tsr)

tsr_rol = tsr.rolling(window=5).mean()
tsr_rol = tsr_rol.shift(-5)
tsr_rol = tsr_rol[:-5]
print("tsr_rol",tsr_rol)
train_data = tsr[:90]
test_data = tsr[90:101]
print("test_data",test_data)

tsr_sea_dec = seasonal_decompose(train_data, model='add', period=12)
tsr_sea_dec.plot()



# Original Series
tsmodel = ExponentialSmoothing(train_data, trend='mul', seasonal='mul', seasonal_periods=12).fit()
prediction = tsmodel.forecast(3)
plt.figure(figsize=(24,10), dpi=100)
plt.ylabel('Values',fontsize=14)
plt.xlabel('Time',fontsize=14)
#plt.plot(tsr_rol,"-", label="Original Series", color="red")
plt.plot(train_data,"-", label="Train Data", color="blue")
plt.plot(test_data,"--", label="Test Data", color="green")
plt.plot(prediction,"-", label="Prediction", color="red")
plt.title("Test Prediction")
plt.legend(title='Legend', fontsize=12)

# Future Series
tsmodel = ExponentialSmoothing(tsr_rol, trend='mul', seasonal='mul', seasonal_periods=12).fit()
prediction = tsmodel.forecast(3)
plt.figure(figsize=(24,10), dpi=100)
plt.ylabel('Values',fontsize=14)
plt.xlabel('Time',fontsize=14)
#plt.plot(tsr_rol,"-", label="Original Series", color="red")
plt.plot(tsr_rol,"-", label="Train Data", color="blue")
plt.plot(prediction,"-", label="Prediction", color="red")
plt.title("Future Prediction")
plt.legend(title='Legend', fontsize=12)
plt.show()