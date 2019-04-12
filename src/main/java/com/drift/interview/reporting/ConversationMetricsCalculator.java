package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();

    long    timeDifference= 0,
            numberOfResponse = 0,
            averageResponseTime = 0;

    for(int i = 0; i < messages.size() - 1; i++) {

      if(messages.get(i).isTeamMember())
        continue;

      for(int k = i + 1; k < messages.size(); k++) {

        if(!messages.get(k).isTeamMember())
          continue;

        timeDifference += messages.get(k).getCreatedAt() - messages.get(i).getCreatedAt();

        numberOfResponse++;

        i = k;

        break;

      }

    } 
    
    
    if(numberOfResponse != 0)
      averageResponseTime = timeDifference / numberOfResponse;

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(averageResponseTime)
        .build();
  }
}
