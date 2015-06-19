'use strict';

/* Controllers */

angular.module('ngo.controllers', []).
    controller('MainController', ['$scope', '$http', '$cookies', function($scope, $http, $cookies) {

        $scope.vm = {};
        $scope.vm.showCropsSection = true;
        $scope.vm.meetingTime = new Date();
        $scope.vm.hideCreateMeetingSection = true;

        if ($cookies.currentUser) {
            $scope.vm.currentUser = $cookies.currentUser;
        }

        $http.get('/v1/districts').success(function(data) {
            $scope.districts = data;
            fixDates($scope.districts);
        });

        $http.get('/v1/crops').success(function(data) {
            $scope.allCrops = data;
            fixDates($scope.allCrops);
        });

        $http.get('/v1/meetings').success(function(data) {
            $scope.meetings = data;
        });

        $scope.predicate = 'district';


        $http.get('/v1/registrations').success(function(data) {
            $scope.allRegistrations = data;
            fixDates($scope.allRegistrations);
        });

        $scope.updateSelection = function() {
            if ($scope.vm.selectedDistrict) {
                $http.get('/v1/registration/' + $scope.vm.selectedDistrict.district).success(function(phoneNumbers) {
                    $scope.phoneNumbers = phoneNumbers;
                });
                $http.get('/v1/districtCrop/' + $scope.vm.selectedDistrict.district).success(function(crops) {
                    $scope.crops = crops;
                });
            }
        };

        $scope.createMeeting = function() {
            var district = $scope.vm.selectedDistrict;
            if (!district) {
                alert('Please enter a district');
                return;
            }
            var mtgdate = $scope.vm.meetingDate;
            var mtgtime = $scope.vm.meetingTime;
            var duration = $('#duration').text();

            var formattedMeetingTime = '';

            if (!mtgdate || !mtgtime) {
                alert('Date & time are required!');
                return;
            } else {
                var monthNames = [ 'Jan', 'Feb', 'March', 'April', 'May', 'June',
                    'July', 'August', 'September', 'October', 'November', 'December'];

                var day = mtgdate.getDate();
                var month = monthNames[mtgdate.getMonth()];
                var year = mtgdate.getFullYear();
                var hours = mtgtime.getHours();
                var minutes = mtgtime.getMinutes();

                formattedMeetingTime = month.toString() + ' ' + day.toString() + ', ' + year.toString() + ', ' + hours.toString() + '-' + minutes.toString();

                if (duration) {
                    formattedMeetingTime += 'X' + duration;
                }
            }

            var notes = $('#note').val();

            var meetingData = {
                'district': district,
                'meetingdate': formattedMeetingTime,
                'note': notes,
                'createdby': $cookies.currentUser,
            };
            $http.post('/v1/meeting', meetingData)
                .success(function(data, status, headers, config) {
                    // reload meeting data
                    $scope.vm.hideCreateMeetingSection = true;
                    $scope.vm.meetingDate = undefined;
                    $scope.vm.meetingTime = undefined;
                    $('#duration').text('');
                    $('#note').val('');

                    $http.get('/v1/meetings').success(function(data) {
                        $scope.meetings = data;
                    });
                })
                .error(function(data, status, headers, config) {
                    alert('Failed to create meeting.');
                });
        };

        $scope.deleteMeeting = function(id) {
            $http.delete('/v1/meeting/' + id)
                .success(function(data, status, headers, config) {
                    alert('Canceled Meeting');
                    return;
                })
                .error(function(data, status, headers, config) {
                    alert('Failed to delete meeting.');
                    return;
                });
        };

        $scope.broadcastMessage = function() {
           var messageData = {
                'districts': $scope.vm.selectedDistrict,
                'message': $('#broadcastMessage').val()
            };

            $http.post('/v1/sendmessagetodistricts', messageData)
                .success(function(data, status, headers, config) {
                    alert('Message Sent');
                    location.reload();
                })
                .error(function(data, status, headers, config) {
                    alert('Failed to send message.');
                    return;
                });
        }

        $scope.getMeetingDetails = function(id) {
             $http.get('/v1/rsvps/' + id)
                .success(function(data, status, headers, config) {
                    $scope.rsvps = data;
                    var modal = '<div id="' + id + '-modal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true"> \
                        <div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close" \
                        ><span aria-hidden="true">&times;</span></button><h4 class="modal-title">RSVPs</h4></div>';

                    modal += '<div class="modal-body">' + data + '</div>'; //this should probably be prettier

                    modal += '<div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div></div></div></div>';

                    $('#rsvpModals').append(modal);

                    $('#' + id + '-modal').modal('show');

                 }).error(function(data, status, headers, config) {
                    alert('failed to get rsvps');
                 });
        };

        $scope.signUpInfo = {};
        $scope.logInInfo = {};

        $scope.signUp = function() {
            $scope.vm.signingUp = true;
            $scope.suEmailRequired = false;
            $scope.suPasswordRequired = false;
            $scope.suTwilioSid = false;
            $scope.suTwilioAuth = false;
            $scope.suPhoneRequired = false;
            $scope.vm.signUpError = '';

            if(!$scope.signUpInfo.email){
                $scope.suEmailRequired = true;
            }
            if(!$scope.signUpInfo.password){
                $scope.suPasswordRequired = true;
            }
            if (!$scope.signUpInfo.confirmPassword) {
                $scope.suConfirmPasswordRequired = true;
            }
            if (!$scope.signUpInfo.twilioSid) {
                $scope.suTwilioSidRequired = true;
            }
            if (!$scope.signUpInfo.twilioAuth) {
                $scope.suTwilioAuthRequired = true;
            }
            if (!$scope.signUpInfo.phone) {
                $scope.suPhoneRequired = true;
            }
            if ($scope.signUpInfo.password != $scope.signUpInfo.confirmPassword) {
                $scope.vm.signUpError = 'Password & confirmation do not match.';
            }

            if (!$scope.suEmailRequired && !$scope.suPasswordRequired && !$scope.suTwilioSidRequired &&
                !$scope.suTwilioAuthRequired && !$scope.suPhoneRequired) {
                var accountSid = $scope.signUpInfo.twilioSid;
                var accountToken = $scope.signUpInfo.twilioAuth;
                var emailAddress = $scope.signUpInfo.email;
                var password = $scope.signUpInfo.password;
                var phoneNumber = $scope.signUpInfo.phone;
                var userData = {
                    'accountSid': accountSid,
                    'accountToken': accountToken,
                    'emailAddress': emailAddress,
                    'password': password,
                    'phoneNumber': phoneNumber
                };
                $http.post('/v1/createUser', userData)
                    .success(function(data, status, headers, config) {
                        $scope.vm.currentUser = phoneNumber;
                        $cookies.currentUser = $scope.vm.currentUser;
                        $scope.signUpInfo = {};
                        $scope.vm.signingUp = false;
                    })
                    .error(function(data, status, headers, config) {
                        if (status == 401) {
                            $scope.vm.signUpError = 'Your Twilio account credentials could not be verified. Make sure you are entering valid credentials that match those on your existing NGO application.';
                        } else {
                            $scope.vm.signUpError = 'Error signing up. Make sure you are using a unique email address and phone number.';
                        }
                        $scope.vm.signingUp = false;
                    });
            } else {
                $scope.vm.signingUp = false;
            }
        };

        $scope.logIn = function() {
            $scope.vm.loggingIn = true;
            $scope.vm.loginError = '';
            $scope.liEmailRequired = false;
            $scope.liPasswordRequired = false;

            if(!$scope.logInInfo.emailAddress){
                $scope.liEmailRequired = true;
            }
            if(!$scope.logInInfo.password){
                $scope.liPasswordRequired = true;
            }

            if (!$scope.liEmailRequired && !$scope.liPasswordRequired) {
                var email = $scope.logInInfo.emailAddress;
                var password = $scope.logInInfo.password;
                var userData = {
                    'emailAddress' : email,
                    'password' : password
                };
                $http.post('/v1/authenticate', userData)
                    .success(function(data, status, headers, config) {
                        var phoneNumber = data;
                        $scope.vm.currentUser = phoneNumber;
                        $cookies.currentUser = $scope.vm.currentUser;
                        $scope.logInInfo = {};
                        $scope.vm.loggingIn = false;
                    })
                    .error(function(data, status, headers, config) {
                        if (status == 401) {
                            $scope.vm.loginError = 'Your email + password combination is not recognized.';
                        } else {
                            $scope.vm.loginError = 'Login is unavailable. Please try again later or contact an administrator.'
                        }
                        $scope.vm.loggingIn = false;
                    });
            } else {
                $scope.vm.loggingIn = false;
            }
        };

        $scope.logout = function() {
            $scope.vm.currentUser = false;
            $cookies.currentUser = '';
        };

        // fix dates to be formatted nicely instead of millisecond amounts.
        // to be used ONLY on arrays of objects that have attributes 'createdate' and 'updatedate'.
        function fixDates(arr) {
            angular.forEach(arr, function(i) {
                i.createdate = formatDate(new Date(i.createdate));
                i.updatedate = formatDate(new Date(i.updatedate));
            });
        }

        function formatDate(d) {
            var HH = d.getHours();
            if (HH < 10) HH = '0' + HH;
            var MM = d.getMinutes();
            if (MM < 10) MM = '0' + MM;
            var SS = d.getSeconds();
            if (SS < 10) SS = '0' + SS;
            var dd = d.getDate();
            if ( dd < 10 ) dd = '0' + dd;
            var mm = d.getMonth()+1;
            if ( mm < 10 ) mm = '0' + mm;
            var yy = d.getFullYear() % 100;
            if ( yy < 10 ) yy = '0' + yy;
            return dd + '-' + mm + '-' + yy + ' ' + HH + ':' + MM + ':' + SS;
        }

        $scope.showCrops = function() {
            $scope.vm.showCropsSection = true;
            $scope.vm.showPhoneNumbersSection = false;
            $scope.vm.showDistrictsSection = false;
            $scope.vm.showMeetingsSection = false;
            $scope.vm.showBroadcastSection = false;
            $scope.vm.showHelpSection = false;
        };

        $scope.showPhoneNumbers = function() {
            $scope.vm.showCropsSection = false;
            $scope.vm.showPhoneNumbersSection = true;
            $scope.vm.showDistrictsSection = false;
            $scope.vm.showMeetingsSection = false;
            $scope.vm.showBroadcastSection = false;
            $scope.vm.showHelpSection = false;
        };

        $scope.showDistricts = function() {
            $scope.vm.showCropsSection = false;
            $scope.vm.showPhoneNumbersSection = false;
            $scope.vm.showDistrictsSection = true;
            $scope.vm.showMeetingsSection = false;
            $scope.vm.showBroadcastSection = false;
            $scope.vm.showHelpSection = false;
        };

        $scope.showMeetings = function() {
            $scope.vm.showCropsSection = false;
            $scope.vm.showPhoneNumbersSection = false;
            $scope.vm.showDistrictsSection = false;
            $scope.vm.showMeetingsSection = true;
            $scope.vm.showBroadcastSection = false;
            $scope.vm.showHelpSection = false;
        };

        $scope.showBroadcast = function() {
            $scope.vm.showCropsSection = false;
            $scope.vm.showPhoneNumbersSection = false;
            $scope.vm.showDistrictsSection = false;
            $scope.vm.showMeetingsSection = false;
            $scope.vm.showBroadcastSection = true;
            $scope.vm.showHelpSection = false;
        };

        $scope.showHelp = function() {
            $scope.vm.showCropsSection = false;
            $scope.vm.showPhoneNumbersSection = false;
            $scope.vm.showDistrictsSection = false;
            $scope.vm.showMeetingsSection = false;
            $scope.vm.showBroadcastSection = false;
            $scope.vm.showHelpSection = true;
        };

    }]);
