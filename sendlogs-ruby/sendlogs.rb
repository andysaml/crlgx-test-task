##########################################################
#    Docker: https://hub.docker.com/_/ruby/
#    docker run -it --rm --name my-running-script -v "$PWD":/usr/src/myapp -w /usr/src/myapp ruby:2.5 ruby your-daemon-or-script.rb
#

require 'net/http'
require 'uri'
require 'json'
require 'securerandom'
require 'ostruct'
require 'logger'

log_file = File.new("sendlogs.log", "w")
$logger = Logger.new log_file
$logger.level = Logger::DEBUG
$logger.formatter = proc do |severity, datetime, progname, msg|
   "NICE: #{msg}\n"
end

def post_request(logs)  
  
  #uri = URI.parse("http://localhost:8080/api/v1/logs")
  uri = URI.parse("https://api.coralogix.com/api/v1/logs")
  https = Net::HTTP.new(uri.host, uri.port)
  https.use_ssl = true
  header = {'Content-Type': 'application/json'}
  request = Net::HTTP::Post.new(uri.request_uri, header)  
  request.body = logs
  begin 
    response = https.request(request)   
	$logger.info('POST request sent')
	rescue Exception => e   
		$logger.info('POST request - error')
		exit
	end 
	$logger.info("Result " + response.code  + ", " + response.body + ", " + response.message + ", " + response.class.name)
  end


def generate_log_entries  
  log_entity_object = {
  'timestamp' => Time.now.to_f*1000,
  'severity' => 1 + rand(4),
  'text' => 'Encountered an error while ' + rand(36**12).to_s(36),
  'category' => [*('A'..'Z')].sample(3).join,
  'className' => 'ClassName-' + [*('a'..'z')].sample(10).join,
  'methodName' => 'MethodName-' + [*('a'..'z')].sample(10).join,
  'threadId' => [*('a'..'z')].sample(1).join + '-' + (100 + rand(900)).to_s
  }
  log_entity_object
end

def append_log_entries
  log_entity_list = Array.new 
  1000.times do
    log_entity_list.push(generate_log_entries)
  end  
  log_entity_list
end

def send_logs
  log_entries_json = '{
           "privateKey": "24cdd646-9f5d-3ff4-c567-d78496d27669",
           "applicationName": "RUBY sending logs",
           "subsystemName": "RUBY subsystem name",
           "computerName": "RUBY computer name",
           "logEntries": [
             {
               "timestamp": 1457827957703.342, 
               "severity": 4,
               "text": "Encountered an error while registering the user john123",
               "category": "DAL",
               "className": "UserManager",
               "methodName": "RegisterUser",
               "threadId": "a-352"
             }
           ]
        }'
        
  json_object = JSON.parse(log_entries_json)
  json_object["logEntries"] = append_log_entries
  3.times do  
	post_request(json_object.to_json)
  end
  
end



send_logs

