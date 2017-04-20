
# coding: utf-8

# In[2]:

from numpy import *

num_activities = 9
num_users = 5

ratings = array( [ [10, 2, 8, 0, 8], [0, 6, 10, 4, 8], [2, 6, 9, 7, 0], [5, 0, 4, 10, 9], [0, 9, 4, 7, 2],
                 [7, 0, 1, 4, 6], [4, 10, 6, 0, 3], [9, 8, 0, 6, 10], [10, 2, 7, 0, 8]])

did_rate = (ratings != 0) * 1


#add some of my ratings
daves_ratings = zeros((num_activities, 1))

daves_ratings[0] = 10   #loves walking
daves_ratings[3] = 6   #liked counting
daves_ratings[6] = 2   #Hated having a snack


#add my ratings to the start of the ratings array
new_ratings = append(daves_ratings, ratings, axis = 1)
new_did_rate = append(((daves_ratings != 0) * 1), did_rate, axis=1)



#function to normalise the ratings, skipping over ratings of 0
def normalise_ratings(new_ratings, new_did_rate):
    
    num_activities = new_ratings.shape[0]
    
    
    ratings_mean = zeros(shape = (num_activities, 1)) #column vector storing mean rating for each activity
    
    
    ratings_normed = zeros(shape = new_ratings.shape) #array of the normalised ratings
    
    for i in range(num_activities):
        
        
        index = where(new_did_rate[i] == 1)[0] #find index where there is a 1 in did_rate
        
        #calculate mean rating of ith activity from users who have rated it
        ratings_mean[i] = mean(new_ratings[i, index])
        ratings_normed[i, index] = new_ratings[i, index] - ratings_mean[i]
        
    return ratings_normed, ratings_mean


# In[3]:

new_ratings, ratings_mean = normalise_ratings(new_ratings, new_did_rate)
old_ratings, old_ratings_mean = normalise_ratings(ratings, did_rate)
#print ratings_mean
print ''
print old_ratings_mean


# In[4]:

print new_ratings


# In[5]:

num_users = new_ratings.shape[1]
num_features = 4

activity_features = random.randn(num_activities, num_features)
user_prefs = random.randn(num_users, num_features)

initial_X_and_theta = r_[activity_features.T.flatten(), user_prefs.T.flatten()]


# In[6]:

def unroll_list(X_and_theta, num_users, num_activities, num_features):
    
    first_bundle = X_and_theta[:num_activities * num_features]
    
    #reshape into correct matrix dimensions
    X = first_bundle.reshape((num_features, num_activities)).transpose()
    
    remaining_bundle = X_and_theta[num_activities * num_features:]
    theta = remaining_bundle.reshape(num_features, num_users).transpose()
    
    return X, theta



def calculate_cost(x_and_theta, new_ratings, new_did_rate, num_users, num_activities,
                      num_features, reg_param):
    X, theta = unroll_list(x_and_theta, num_users, num_activities, num_features)
    
    
    #We multiply (element-wise) by did_rate because we only want to work with recorded ratings
    # X.dot(theta.T) * did_rate is our predictions matrix, which we subtract from ratings to get our error
    # We then square the matrix in order to get rid of negative signs.
    # ** means an element-wise power
    cost = sum( ( X.dot(theta.T) * new_did_rate - new_ratings) ** 2) / 2
    
    return cost



def calculate_gradient(x_and_theta, new_ratings, new_did_rate, num_users, num_activities,
                      num_features, reg_param):
    X, theta = unroll_list(x_and_theta, num_users, num_activities, num_features)
    
    difference = X.dot(theta.T) * new_did_rate - new_ratings
    X_gradient = difference.dot(theta) + reg_param * X
    theta_gradient = difference.T.dot(X) + reg_param * theta
    
    return r_[X_gradient.T.flatten(), theta_gradient.T.flatten()]


# In[7]:

from scipy import optimize
reg_param = 1


# In[8]:

#making the recommendations
minimised_cost_and_optimal_params = optimize.fmin_cg(calculate_cost, fprime=calculate_gradient, x0=initial_X_and_theta,
                                                   args = (new_ratings, new_did_rate, num_users, num_activities, 
                                                           num_features, reg_param), 
                                                           maxiter=100, disp=True, full_output=True)


# In[9]:

#Split cost and movie_features+user_prefs up.
cost, optimal_activity_features_and_user_prefs = minimised_cost_and_optimal_params[1], minimised_cost_and_optimal_params[0]


# In[10]:

#Split movie_features and user_prefs up
activity_features, user_prefs = unroll_list(optimal_activity_features_and_user_prefs, num_users, num_activities, num_features)


# In[11]:

all_predictions = activity_features.dot(user_prefs.T)
print all_predictions


# In[12]:

#take my column from the predictions matrix, and add the mean back 
#to each item to unnormalise the results
my_predictions = all_predictions[:, 0:1] + ratings_mean

print my_predictions
print ' '
print ratings_mean


# In[103]:

def loadActivities():
    activity_dict = {}
    activity_index = 0
    with open('activities.txt', 'r') as yo:
        file_contents = yo.readlines()
        for content in file_contents:
            activity_dict[activity_index] = content.strip().split(' ', 1)[1]
            activity_index += 1
            
    return activity_dict


# In[104]:

all_activities = loadActivities()

print all_activities


# In[105]:

sorted_indexes = my_predictions.argsort(axis=0)[::-1]
#my_predictions = my_predictions[sorted_indexes]


# In[98]:

for i in range(num_activities):
    index = i
    print "Predicting rating of %.lf for activity %s" % (my_predictions[index], all_activities[index])
    


# In[ ]:




# In[ ]:




# In[ ]:



