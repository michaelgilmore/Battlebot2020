#!/usr/bin/perl
use strict;
use warnings;

use Time::Local;
use Clone 'clone';

################################################################################################
# Hidden Message - Morse Code
################################################################################################

################################################################################################
# Step One
# Given a string that has been converted to Morse code, find all of the possible and unique sequences of
# remaining tokens after removing the second message from the first. There are 3 different types of
# tokens in the Morse code message.
# • Dot (*)
# • Dash (-)
# • Blank (_)
# Every letter in the message is separated by a single blank character and every word is separated by 3
# blank characters.
# Example:
# • Given: AB *-_-***
# • Remove: R *-*
# • This can be done 6 different ways.
# o X X _ - X * * o X X _ - * X * o X X _ - * * X
# o X - _ X X * * o X - _ X * X * o X - _ X * * X
# • But this results in only 2 possible unique sequences of remaining tokens.
# o _ - * * o - _ * *
# • So the final result for this example would be 2.
# 
# Write a program that can calculate all of the deletion paths for removing one Morse code message
# from another. This program should be able to calculate all of the paths in the example below in under
# 10 seconds and return the total number of paths found. This is a preliminary step for your program, for
# testing.
# • Given: Hello World
# • Remove: Help
# o ****_*_*-**_*- **_-- -___*- -_- -- _*-*_*- **_-**
# o ****_*_*-**_*- -*
# • ANSWER: 1311
# 
################################################################################################
# Step Two
# Find the possible deletion paths where you must remove a second message from the remaining tokens
# in the original message after removing the first phrase, including blank tokens. All remaining tokens
# would be kept in the same order to find a second phrase. Return all of the possible and unique
# sequences of remaining tokens after removing both phrases.
# In the example above where R is removed from AB notice that there are 6 delete paths but only 2
# unique sequences of remaining tokens.
# Example:
# • Given: ABCD
# • Remove: ST
# • Then Remove: ZN
# • One solution path would look like:
# o *-_- ***_-*- *_-**
# o ***_-
# o -- **_-*
# o Start:
# o Remove ST:
# o Then Remove ZN:
# o The set of remaining characters
# ? * - _ - * * * _ - * - * _ - * *
# ? x - _ - x x * x x * - * _ - * *
# ? x x _ x x x x x x x - * x x x *
# ? _ - * *
# • There are 5 sequences of remaining characters for this example:
# o _-** o _*-* o -_** o *_-* o *-_*
# Expand your program to find all of the possible sequences of remaining characters after removing 2
# hidden Morse code messages from an original message. This program should be able to calculate all of
# the sequences in the example below in less than 60 seconds and return the total number of distinct and
# valid sequences found. This will be the final expected output from the program.
# • Given: The Star Wars Saga o -_****_*___***_- _*-_*- *___*-- _*-_*- *_***___***_*-_- -*_*-
# • Remove: Yoda o -*- -_- -- _-**_*-
# • And Remove: Leia o *-**_*_**_*-
# • Expected Answer: 11474
# 
# Program Specifications
# • Input: Three command-line arguments denoting the original message, the first hidden message,
# and the second hidden message. The three messages will be in Morse code using the
# representation described here.
# • Expected Output: Total number of distinct and valid remaining token sequences in the original
# message.
# • Bounds: Original Message less than 100 Morse code characters.

################################################################################################
# Test cases for Step 1
# 1.1. No parameters
# 1.2. Only one parameter which is valid
# 1.3. Only one parameter which is invalid
# 1.4. Two parameters, neither is valid
# 1.5. Two parameters, first is valid, second is invalid
# 1.6. Two parameters, first is invalid, second is valid
# 1.7. Two parameters, both valid
#

################################################################################################
# Additional test cases for Step 2
# 2.1. A third parameter that is invalid
# 2.2. A third parameter that is valid
# ...
#

my $usage = <<'END_USAGE';
Step One Usage
----------------------------------------------------------
this_script.pl [source] [hidden_msg]

This script removes the hidden message from the source string in as many ways as possible and gives a count of the number of unique Morse Code sequences that result.

source = a string of Morse Code made up only of dits, dahs, and spaces
hidden_msg = a string of Morse Code made up only of dits, dahs, and spaces that should be found within the source string
dit = the '*' character
dah = the '-' character
space = the '_' character

There should be a single space character between each Morse Code letter and three space characters between each Morse Code word.

Example:
this_script.pl ****_*_*-**_*-**_---___*-_-_****_*_-*_*-_****_*_*-_*-**_-_**** **-*_**_-*_-**___--_*
source = hello athenahealth
hidden_msg = find me


----------------------------------------------------------
Step Two Usage
----------------------------------------------------------
this_script.pl [source] [hidden_msg] [second_hidden_msg]

This script removes the first hidden message from the source string in as many ways as possible. Then removes the second hidden message from each of those unique results in the same manner giving a combined count of all unique Morse Code sequences that result.

END_USAGE


################################################################################################
# The script

my $start_time = timegm(gmtime());
print "Beginning Hidden String process...\n";

my $source = shift || '';
my $hidden1 = shift || '';
my $hidden2 = shift || '';

readInput();

# Declare a bunch of global variables to minimize memory allocation in recursive function
my $end_of_src_no_message = 0;
my $leftover = '';
my $hid_msg_len = length $hidden1;
my $i;
my $i_to_bump = ($hid_msg_len-1);#initializing to last index
my $recur_count = 0;
my $result_count = 0;
my $src_index = 0;
my $src_msg_len = length $source;
my $start_index = 0;

my @hid_array = split //, $hidden1;
my @indices = (0..($hid_msg_len-1));

my %result_hash;
my %result_hash2;

# For debugging Java version
my $count_per_zero_index = 0;

# Here is where all the work is done
my $count = countHiddenStrings();


# Output result from Step One
print "\nStep One result = $count\n";
#foreach my $res1 (keys %result_hash) {print "$res1\n";}
print "recur count = $recur_count\n";
my $run_time_seconds = (timegm(gmtime()) - $start_time);
printf("Finished Step One (runtime = %02d:%02d)\n", ($run_time_seconds/60), ($run_time_seconds%60));
print "\n";


# Step Two?
if(defined $hidden2 && $hidden2 ne '') {
    $hidden1 = $hidden2;
    undef $hidden2;
    
    %result_hash2 = %{ clone (\%result_hash) };
    %result_hash = ();
    $count = 0;
    
    foreach my $result (keys %result_hash2) {
        resetVariables();
        $source = $result;
        print "Running with:\nsrc = '$source'\nhidden1 = '$hidden1'\n\n";
        $count += countHiddenStrings();
        print "Partial result = $count\n\n";
    }

    # Output result from Step Two
    print "\nStep Two result = $count\n";
    #foreach my $res2 (keys %result_hash) {print "$res2\n";}
    $run_time_seconds = (timegm(gmtime()) - $start_time);
    printf("Finished Step Two (runtime = %02d:%02d)\n", ($run_time_seconds/60), ($run_time_seconds%60));
}


################################################################################################
# Functions

sub readInput {
    if($source eq '') {
        exitWithErrorAndUsage("FATAL: You must pass in a string from which to find a hidden message.", 1);
    }
    
    checkForTestRun();

    if($hidden1 eq '') {
        $hidden1 = shift or exitWithErrorAndUsage("FATAL: You must pass in a hidden string to find within the source string.", 1);
    }

    checkInputTags();

    print "Running with:\nsrc = '$source'\nhidden1 = '$hidden1'\n";
    if($hidden2 ne '') {
        print "hidden2 = '$hidden2'\n";
    }
    print "\n";

    validateInput();
}

sub exitWithErrorAndUsage {
    print shift."\n";
    print $usage;
    exit shift;
}

sub toMorse {
    my $input = uc shift;
    print "Converting '$input' to Morse Code:\n";
    my $output = '';

    for my $ch (split //, $input) {
        if(length $output > 0 ) {$output .= '_';}
        if($ch eq 'A') {$output .= '*-';}
        if($ch eq 'B') {$output .= '-***';}
        if($ch eq 'C') {$output .= '-*-*';}
        if($ch eq 'D') {$output .= '-**';}
        if($ch eq 'E') {$output .= '*';}
        if($ch eq 'F') {$output .= '**-*';}
        if($ch eq 'G') {$output .= '--*';}
        if($ch eq 'H') {$output .= '****';}
        if($ch eq 'I') {$output .= '**';}
        if($ch eq 'J') {$output .= '*---';}
        if($ch eq 'K') {$output .= '-*-';}
        if($ch eq 'L') {$output .= '*-**';}
        if($ch eq 'M') {$output .= '--';}
        if($ch eq 'N') {$output .= '-*';}
        if($ch eq 'O') {$output .= '---';}
        if($ch eq 'P') {$output .= '*--*';}
        if($ch eq 'Q') {$output .= '--*-';}
        if($ch eq 'R') {$output .= '*-*';}
        if($ch eq 'S') {$output .= '***';}
        if($ch eq 'T') {$output .= '-';}
        if($ch eq 'U') {$output .= '**-';}
        if($ch eq 'V') {$output .= '***-';}
        if($ch eq 'W') {$output .= '*--';}
        if($ch eq 'X') {$output .= '-**-';}
        if($ch eq 'Y') {$output .= '-*--';}
        if($ch eq 'Z') {$output .= '--**';}
        if($ch eq ' ') {$output .= '__';}
    }
    print $output;
}

sub checkInputTags {
    if($source eq '-toMorse') {
        toMorse($hidden1);
        exit;
    }
    
    if($source eq '-?') {
        print $usage;
        exit;
    }
}

sub checkForTestRun {
    if($source eq '-testA') {
        $source = '*-_-***'; #AB
        $hidden1 = '*-*'; #R
        print "TestA - Expected Answer: 2\n";
    }
    elsif($source eq '-testB') {
        $source = '****_*_*-**_*-**_---____*--_---_*-*_*-**_-**'; #Hello World
        $hidden1 = '****_*_*-**_*--*'; #Help
        print "TestB - Expected Answer: 1311\n";
    }
    elsif($source eq '-testC') {
        $source = '*-_-***_-*-*_-**'; #ABCD
        $hidden1 = '***_-'; #ST
        $hidden2 = '--**_-*'; #ZN
        print "TestC - Expected Answer: 5\n";
    }
    elsif($source eq '-testD') {
        #This is one from Step Two of TestC.
        $source = '-_-**-*_-**'; #
        $hidden1 = '--**_-*'; #ZN
        print "TestD - Expected Answer: 2\n";
    }
    elsif($source eq '-testE') {
        $source = '-_****_*___***_-_*-_*-*___*--_*-_*-*_***___***_*-_--*_*-'; #The Star Wars Saga
        $hidden1 = '-*--_---_-**_*-'; #Yoda
        $hidden2 = '*-**_*_**_*-'; #Leia
        print "TestE - Expected Answer: 11474\n";
    }
    elsif($source eq '-testF') {
        $source = '-_****_*___***_-_*-_*-*___*--_*-_*-*_***___***_*-_--*_*-'; #The Star Wars Saga
        $hidden1 = '-*--_---_-**_*-'; #Yoda
        print "TestE - Expected Answer: 11474\n";
    }
}

sub countHiddenStrings {
    $recur_count += 1;
    if($indices[0] > $src_msg_len - $hid_msg_len) {
        #print "first index reached limit\n";
        return 0;
    }
    #for debugging
    #print "starting with these indices:";
    #foreach(@indices) {print "$_,";}
    #print "\n";

    $start_index = 0;
    $leftover = '';
    $i_to_bump = ($hid_msg_len-1);
    
    #find next set of indices
    $end_of_src_no_message = 0;
    for $i (0..(@hid_array-1)) {
        #print "i=$i\n";
        $src_index = index($source, $hid_array[$i], $indices[$i]);
        if($src_index == -1) {
            if($i == 0) {
                #print "we have passed the last of the first character\n";
                return 0;
            }
            $i_to_bump = $i - 1;
            $end_of_src_no_message = 1;
            #print "src_index = -1\n";
            last;
        }
        
        $leftover .= substr($source, $start_index, $src_index-$start_index);
        if($indices[$i] != $src_index) {
            #print "call0 bump $i to $src_index\n";
            if(!bumpIndices($i, $src_index)) {
                return 0;
            }
        }
        $start_index = $src_index + 1;
    }
    #for debugging
    #print "indices:";
    #foreach(@indices) {print "$_,";}
    #print "\n";

    if($end_of_src_no_message != 1) {
        $leftover .= substr($source, $start_index, $src_msg_len - $start_index + 1);
        if(!exists $result_hash{$leftover}) {
            $result_count = scalar keys %result_hash;
            $result_hash{$leftover} = 1;
            #print "    \$\$\$\$ Pattern '$leftover' added (recur count = $recur_count)\n" ;
            print "Pattern $result_count '$leftover' added (recur count = $recur_count)\n" ;
            #print "call2 bump $i_to_bump to ".($indices[$i_to_bump]+1)."\n";
            if(bumpIndices($i_to_bump, $indices[$i_to_bump]+1)) {
                return 1 + countHiddenStrings();
            }
            return 1;
        }
        else {
            #print "Pattern '$leftover' already there\n" ;
        }
    }
    
    #print "call3 bump $i_to_bump to ".($indices[$i_to_bump]+1)."\n";
    if(bumpIndices($i_to_bump, $indices[$i_to_bump]+1)) {
        return countHiddenStrings();
    }
    return 0;
}

sub bumpIndices {
    my($start_i, $start_value) = @_;
    #print "bumping($start_i,$start_value)\n";
    if($start_value >= $src_msg_len) {
        if($start_i == 0) {
            #print "can't bump first index beyond end of source message\n";
            return 0;
        }
        #print "can't bump index($start_i) beyond end of source message, adjusting($start_i-1,$indices[$start_i]+1)\n";
        $start_i -= 1;
        $start_value = $indices[$start_i]+1;
    }
    foreach my $i ($start_i..(@indices-1)) {
        if($i == 0) {
            my $sec = (timegm(gmtime()) - $start_time);
            #printf("%02d:%02d ", ($sec/60), ($sec%60));
            #print "bumping $i from $indices[$i] to $start_value\n";
        }
        @indices[$i] = $start_value;
        $start_value += 1;
    }
    return 1;
}

sub resetVariables {
    $src_msg_len = length $source;
    $hid_msg_len = length $hidden1;
    @hid_array = split //, $hidden1;
    @indices = (0..($hid_msg_len-1));
    $recur_count = 0;
}

sub validateInput {
    length $source <= 100 or die "FATAL: Source string too long. Should be 100 characters or less.\n";
    $source =~ /\A[*-_]+\z/ or exitWithErrorAndUsage("FATAL: Your source string should only contain dits(*), dahs(-), or spaces.", 1);
    $hidden1 =~ /\A[*-_]+\z/ or exitWithErrorAndUsage("FATAL: Your hidden string should only contain dits(*), dahs(-), or spaces.", 1);
}
