# coding: utf-8
# Prepare the raw geolife data to be imported into postgresql  
import pandas as pd
import os
import datetime

def preprocess(address, MAX_INTERVAL = 720):
    all_trajs = pd.DataFrame(columns = ['latitude','longitude','zero','altitude','date_in_days','date_complete','time_complete','seconds_of_day','seconds_diff','userid','trajid'])
    diffs = list()
    pplCount = -1
    user = -1
    stats = pd.DataFrame(columns=['userid','trajs_count','locs_count','duration_in_days'])
    for root, dirs, files in os.walk(address, topdown=False):
        print(root)
        if(len(root.split('\\')) > 3):
            new_user = int(root.split('\\')[3])
        else:
            continue
        if(new_user != user):
            if(user!=-1):
                print(user)
                all_trajs.to_csv(str(user)+'.csv',index = False)
                stats.loc[user]= [user,trajCount,len(all_trajs.index),max(all_trajs['date_in_days'])-min(all_trajs['date_in_days'])]   
            trajCount = 0
            all_trajs = pd.DataFrame(columns = ['latitude','longitude','zero','altitude','date_in_days','date_complete','time_complete','seconds_of_day','seconds_diff','userid','trajid'])
            

        for name in files:
            path = os.path.join(root, name)
            if(name=='labels.txt'):
                modes_of_transportation = pd.read_csv(path,header=1,delimiter = '\t')
                modes_of_transportation.columns = ['start_datetime','end_datetime','mode']
                modes_of_transportation['userid'] = new_user
                modes_of_transportation.to_csv(str(new_user)+'_modes.csv',index = False)    
                continue
            #print(name)
            individual_traj = pd.read_csv(path,header=6)
            #print(individual_traj.head())
            #raw_input("wait...")
            individual_traj.columns = ['latitude','longitude','zero','altitude','date_in_days','date_complete','time_complete']
            individual_traj['seconds_of_day'] = individual_traj['time_complete'].apply(lambda x:(datetime.datetime.strptime(x, '%H:%M:%S') - datetime.datetime.strptime('00:00:00', '%H:%M:%S')).total_seconds())
            individual_traj['seconds_diff'] = individual_traj['seconds_of_day'].diff()
            individual_traj = individual_traj.fillna(MAX_INTERVAL+1)
            #print(individual_traj.head())
            #print("before")
            #print(sum((individual_traj['longitude'] == 0) | (individual_traj['latitude'] == 0)))
            individual_traj = individual_traj[(individual_traj['longitude'] != 0) & (individual_traj['latitude'] != 0)] 
            #print("after")
            #print(sum((individual_traj['longitude'] == 0) | (individual_traj['latitude'] == 0)))                
            #traj_starts = individual_traj[individual_traj['diff'] >  MAX_INTERVAL].index 
            #print(traj_starts)
            user = new_user
            individual_traj['userid'] = user
            individual_traj['trajid'] = trajCount
            trajCount = trajCount + 1
            all_trajs = pd.concat([all_trajs,individual_traj])
    #return(all_trajs)
    return(stats)


def main():
    #Read the data, preprocess it, get some stats and store them in files
    #One file per user's trajs
    #One stat files for all
    stats  = preprocess('.\Geolife Trajectories 1.3\Data')
    stats.to_csv('stats.csv')

if __name__ == "__main__": main()
