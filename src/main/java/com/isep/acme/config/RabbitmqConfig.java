package com.isep.acme.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
             Jackson2JsonMessageConverter jackson2JsonMessageConverter,
             MessagePostProcessor beforePublishPostProcessor) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.addBeforePublishPostProcessors(beforePublishPostProcessor);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessagePostProcessor beforePublishPostProcessor(String instanceId){
        return new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message){
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setAppId(instanceId);
                return message;
            }
        };
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange voteCreatedExchange() {
        return new FanoutExchange("vote.vote-created");
    }

    @Bean
    public Queue voteCreatedQueue(String instanceId){
        return new Queue("vote.vote-created.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingVoteCreatedToVoteCreated(FanoutExchange voteCreatedExchange, Queue voteCreatedQueue) {
        return BindingBuilder.bind(voteCreatedQueue).to(voteCreatedExchange);
    }


    @Bean
    public FanoutExchange voteUpdatedExchange() {
        return new FanoutExchange("vote.vote-updated");
    }

    @Bean
    public Queue voteUpdatedQueue(String instanceId) {
        return new Queue("vote.vote-updated.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingvoteUpdatedtovoteUpdated(FanoutExchange voteUpdatedExchange,
            Queue voteUpdatedQueue) {
        return BindingBuilder.bind(voteUpdatedQueue).to(voteUpdatedExchange);
    }

    @Bean
    public FanoutExchange voteDeletedExchange() {
        return new FanoutExchange("vote.vote-deleted");
    }

    @Bean
    public Queue voteDeletedQueue(String instanceId) {
        return new Queue("vote.vote-deleted.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public FanoutExchange reviewCreatedExchange() {
        return new FanoutExchange("review.review-created");
    }

    @Bean
    public Queue reviewCreatedQueue(String instanceId) {
        return new Queue("review.review-created.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewCreatedToReviewCreated(FanoutExchange reviewCreatedExchange, Queue reviewCreatedQueue) {
        return BindingBuilder.bind(reviewCreatedQueue).to(reviewCreatedExchange);
    }

    @Bean
    public FanoutExchange reviewUpdatedExchange() {
        return new FanoutExchange("review.review-updated");
    }

    @Bean
    public Queue reviewUpdatedQueue(String instanceId) {
        return new Queue("review.review-updated.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewUpdatedtoReviewUpdated(FanoutExchange reviewUpdatedExchange,
            Queue reviewUpdatedQueue) {
        return BindingBuilder.bind(reviewUpdatedQueue).to(reviewUpdatedExchange);
    }

    @Bean
    public FanoutExchange reviewDeletedExchange() {
        return new FanoutExchange("review.review-deleted");
    }

    @Bean
    public Queue reviewDeletedQueue(String instanceId) {
        return new Queue("review.review-deleted.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingReviewDeletedtoReviewDeleted(FanoutExchange reviewDeletedExchange,
            Queue reviewDeletedQueue) {
        return BindingBuilder.bind(reviewDeletedQueue).to(reviewDeletedExchange);
    }

    // SAGA

    // Fanout Exchange and Queues to receive and send created temporary votes
    @Bean
    public FanoutExchange temporaryVoteCreatedExchange() {
        return new FanoutExchange("temporary-vote.temporary-vote-created");
    }

    @Bean
    public Queue temporaryVoteCreatedQueue(String instanceId) {
        return new Queue("temporary-vote.temporary-vote-created.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingTemporaryVoteCreatedToTemporaryVoteCreated(FanoutExchange temporaryVoteCreatedExchange,
            Queue temporaryVoteCreatedQueue) {
        return BindingBuilder.bind(temporaryVoteCreatedQueue).to(temporaryVoteCreatedExchange);
    }

    // Direct exchange and a queue to receive review created for a temporary vote
    @Bean
    public FanoutExchange reviewCreatedForTemporaryVoteExchange() {
        return new FanoutExchange("review.review-created-for-temporary-vote");
    }

    @Bean
    public Queue reviewCreatedForTemporaryVoteQueue() {
        return new Queue("review.review-created-for-temporary-vote.vote-command", true, false, false);
    }

    @Bean
    public Binding bindingReviewCreatedForTemporaryVoteToReviewCreatedForTemporaryVote(FanoutExchange reviewCreatedForTemporaryVoteExchange,
            Queue reviewCreatedForTemporaryVoteQueue) {
        return BindingBuilder.bind(reviewCreatedForTemporaryVoteQueue).to(reviewCreatedForTemporaryVoteExchange);
    }

    // Exchange and a queue to receive a definite vote
    @Bean
    public FanoutExchange definitiveVoteCreatedExchange() {
        return new FanoutExchange("vote.definitive-vote-created");
    }

    @Bean
    public Queue definitiveVoteCreatedQueue(String instanceId) {
        return new Queue("vote.definitive-vote-created.vote-command." + instanceId, true, true, true);
    }

    @Bean
    public Binding bindingDefinitiveVoteCreatedToDefinitiveVoteCreated(FanoutExchange definitiveVoteCreatedExchange,
            Queue definitiveVoteCreatedQueue) {
        return BindingBuilder.bind(definitiveVoteCreatedQueue).to(definitiveVoteCreatedExchange);
    }

}
