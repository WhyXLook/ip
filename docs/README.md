# BasilSeed User Guide

BasilSeed is a task management application supporting ToDos, Deadlines, and Events. It allows users to add, mark, 
unmark, delete, search, list, and archive tasks. Tasks are persisted in storage and automatically loaded on startup.

## General format rules
- commands are to be in lowercase only
- parameter keyword order (e.g. "/from" & "/to") matters.
- For commands that do not take in parameters such as `list`, `bye`, `archive`,
anything text after it will be ignored

## Listing current tasks

This lists all current tasks. 
It has no parameters.

Example: `list`

```
1.[T][] borow book
2.[E][] project meeting /from Dec 05 2025 /to Dec 06 2025
```

## Marking tasks as done

This feature is to mark tasks as done.

Example: `mark 1`

Note that it only accepts one parameter which is an integer index of the task
this index cannot be more than the size of the task list or be less than 1.
This parameter also does not accept any commas or spacing.


The output shows you the task that you have marked as done

```
Nice! I've marked this task as done: [T][X] borrow book
```

## Marking tasks as not done

This feature is to mark tasks as not done.

Example: `unmark 1`

Note that it only accepts one parameter which is an integer index of the task
this index cannot be more than the size of the task list or be less than 1.
This parameter also does not accept any commas or spacing.


The output shows you the task that you have marked as not done

```
OK, I've marked this task as not done yet: [T][] borrow book
```

## Adding ToDos

ToDos are the most basic type of task that has no deadline.
You can simply mark it as undone or done.
Anything after the `todo` command will be registered as its task name.

Example: `todo borrow book`
In this case `borrow book` will be the task name


You will see the task you have added and an update on how many tasks you have in the list now
Expected output:
```
Got it. I've added this task:
[T][] borrow book
Now you have 4 tasks in the list
```

## Adding Deadlines

Deadlines are the type of task that has a deadline which is specified with the /by parameter keyword
You can also mark it as undone or done.
Anything after the `deadline` command and before the `/by` keyword will be registered as its task name
After `/by` you are to give a date string in the form of yyyy-MM-dd

Example: `deadline borrow book /by 2025-12-06`
In this case `borrow book` will be the task name
`2025-12-06` will be the deadline date

You will see the task you have added and an update on how many tasks you have in the list now
Expected output:
```
Got it. I've added this task:
[D][] borrow book /by Dec 06 2025
Now you have 4 tasks in the list
```

## Adding Events

Events are the type of task that have a "from" date (specified by the keyword argument `/from`) 
and a "to" date (specified by the keyword argument `/to`)
You can also mark it as undone or done.
Anything after the `event` command and before the `/from` keyword will be registered as its task name
After `/from` you are to give a date string in the form of yyyy-MM-dd
After `/to` you are to give a date string in the form of yyyy-MM-dd
You are required to fill both of these parameters up

Example: `event borrow book /from 2025-12-05 /to 2025-12-06`
In this case `borrow book` will be the task name
`2025-12-05` will be the from date and `2025-12-06` will be the to date.

You will see the task you have added and an update on how many tasks you have in the list now
Expected output:
```
Got it. I've added this task:
[E][] borrow book /from Dec 05 2025 /to Dec 06 2025
Now you have 4 tasks in the list
```

## Deleting Tasks

To delete a task in the task list.

Example: `delete 1`

Note that it only accepts one parameter which is an integer index of the task
this index cannot be more than the size of the task list or be less than 1.
This parameter also does not accept any commas or spacing.


The output shows you the task that you have deleted and the new amount of tasks.

```
Noted. I've removed task:
[D][] borrow book /by Dec 06 2025
Now you have 3 tasks in the list
```

## Searching for tasks

Search for specific tasks with a keyword.
This search processes through the entire string representation of the task.

Example: `find book`
`find /by`

The output will show you tasks that matches this search.
Note that for something like `find /by` it will hit deadline tasks as they have a `/by` keyword 
in their task representation.

Output below shows `find book`
```
1.[T][] borow book
2.[E][] return book /from Dec 05 2025 /to Dec 06 2025
```

## Archiving current tasks

This archives all current tasks.
It will save to archive.txt under /data.
All current tasks will be wiped.

Example: `archive`

```
1.[T][] borow book
2.[E][] project meeting /from Dec 05 2025 /to Dec 06 2025

These tasks has been archived
```